package suntrix.api.gateway.proxies

import io.ktor.resources.*
import io.ktor.server.application.*
import io.ktor.server.resources.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import io.ktor.utils.io.core.*
import kotlinx.coroutines.async
import kotlinx.serialization.Serializable
import suntrix.api.gateway.target.omdb.OMDBApiClient
import suntrix.api.gateway.target.tmdb.TMDBApiClient

/**
 * Created by Sebastian Owodzin on 06/03/2022
 */

@Serializable
@Resource("/movie/{title}/{releaseYear}")
data class MovieRoute(
    val title: String,
    val releaseYear: Int,
    val debug: Boolean = false
)

fun Application.movieProxy() {

    val omdbApiClient = OMDBApiClient("ac6ddc16")
    val tmdbApiClient = TMDBApiClient("eeb9798510a128cad0ab19d19db2f563")

    routing {
        get<MovieRoute> {
            val omdbMovieDeferred = async { omdbApiClient.use { api -> api.searchMovie(it.title, it.releaseYear) } }
            val tmdbMovieDeferred = async { tmdbApiClient.use { api -> api.searchMovie(it.title, it.releaseYear) } }

            val omdbData = omdbMovieDeferred.await()
            val tmdbData = tmdbMovieDeferred.await()

//            val omdbData = omdbApiClient.use { api -> api.searchMovie(it.title, it.releaseYear) }
//            val tmdbData = tmdbApiClient.use { api -> api.searchMovie(it.title, it.releaseYear) }

            val ratings = omdbData.ratings.map { Response.Rating(it.source, it.value) }.plus(
                tmdbData.results.firstOrNull { it.title == omdbData.title }?.run {
                    listOf(Response.Rating("The Movie Database", "$voteAverage/10"))
                } ?: emptyList()
            )

            call.respond(
                Response(
                    Response.Data(
                        omdbData.title,
                        omdbData.year.toInt(),
                        omdbData.released,
                        omdbData.runtime.runtimeMinutes() ?: -1,
                        omdbData.plot,
                        omdbData.poster,
                        ratings
                    ),
                    if (it.debug) {
                        Response.OriginData(
                            omdbData,
                            tmdbData
                        )
                    } else null
                )
            )
        }
    }
}

private fun String.runtimeMinutes(): Int? =
    """(\d+) min""".toRegex().find(this)?.destructured?.component1()?.toInt()

@Serializable
data class Response(
    val data: Data,
    val originData: OriginData? = null
) {

    @Serializable
    data class Data(
        val title: String,
        val releaseYear: Int,
        val releaseDate: String,
        val runtime: Int,
        val plot: String,
        val posterUrl: String,
        val ratings: List<Rating> = emptyList()
    )

    @Serializable
    data class Rating(
        val source: String,
        val value: String,
    )

    @Serializable
    data class OriginData(
        val omdbData: suntrix.api.gateway.target.omdb.MovieResponse,
        val tmdbData: suntrix.api.gateway.target.tmdb.MovieResponse
    )
}
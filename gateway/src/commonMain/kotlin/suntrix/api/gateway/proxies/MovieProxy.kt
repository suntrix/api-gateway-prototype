package suntrix.api.gateway.proxies

import io.ktor.resources.*
import io.ktor.server.application.*
import io.ktor.server.resources.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.serialization.Serializable
import suntrix.api.gateway.target.default.ApiClient
import suntrix.api.gateway.target.omdb.omdb_searchMovie
import suntrix.api.gateway.target.tmdb.tmdb_searchMovie
import suntrix.api.gateway.target.omdb.MovieResponse as omdb_MovieResponse
import suntrix.api.gateway.target.tmdb.MovieResponse as tmdb_MovieResponse

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

    val apiClient = ApiClient()
    val omdbApiKey = "ac6ddc16"
    val tmdbApiKey = "eeb9798510a128cad0ab19d19db2f563"

    routing {
        get<MovieRoute> {
            val (omdbData, tmdbData) = listOf(
                async {
                    apiClient.omdb_searchMovie(it.title, it.releaseYear, omdbApiKey)
                },
                async {
                    apiClient.tmdb_searchMovie(it.title, it.releaseYear, tmdbApiKey)
                }
            ).awaitAll()

            call.respond(
                buildResponse(
                    omdbData as omdb_MovieResponse,
                    tmdbData as tmdb_MovieResponse,
                    it.debug
                )
            )
        }
    }
}


private fun buildResponse(
    omdbData: omdb_MovieResponse,
    tmdbData: tmdb_MovieResponse,
    debug: Boolean
) = Response(
    Response.Data(
        omdbData.title,
        omdbData.year.toInt(),
        omdbData.released,
        omdbData.runtime.runtimeMinutes(),
        omdbData.plot,
        omdbData.poster,
        buildRatings(omdbData, tmdbData)
    ),
    if (debug) {
        Response.OriginData(
            omdbData,
            tmdbData
        )
    } else null
)

private fun buildRatings(
    omdbData: omdb_MovieResponse,
    tmdbData: tmdb_MovieResponse
): List<Response.Rating> = omdbData.ratings.map { Response.Rating(it.source, it.value) }.plus(
    tmdbData.results.firstOrNull { it.title == omdbData.title }?.run {
        listOf(Response.Rating("The Movie Database", "$voteAverage/10"))
    } ?: emptyList()
)

private fun String.runtimeMinutes(): Int? = """(\d+) min""".toRegex().find(this)?.destructured?.component1()?.toInt()


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
        val runtimeMinutes: Int?,
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
        val omdbData: omdb_MovieResponse,
        val tmdbData: tmdb_MovieResponse
    )
}

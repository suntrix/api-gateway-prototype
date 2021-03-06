package suntrix.api.gateway.target.tmdb

import io.ktor.client.call.*
import io.ktor.client.request.*
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import suntrix.api.gateway.target.default.ApiClient

/**
 * Created by Sebastian Owodzin on 06/03/2022
 */
suspend fun ApiClient.tmdb_searchMovie(title: String, releaseYear: Int, apiKey: String): MovieResponse =
    httpClient.get("https://api.themoviedb.org/3/search/movie") {
        url {
            parameter("query", title)
            parameter("primary_release_year", releaseYear)
            parameter("api_key", apiKey)
        }
    }.body()

@Serializable
data class MovieResponse(
    val page: Int,
    val results: List<Movie>,
    @SerialName("total_pages") val totalPages: Int,
    @SerialName("total_results") val totalResults: Int
) {

    @Serializable
    data class Movie(
        val adult: Boolean,
        @SerialName("backdrop_path") val backdropPath: String?,
        @SerialName("genre_ids") val genreIds: List<Int>,
        val id: Int,
        @SerialName("original_language") val originalLanguage: String,
        @SerialName("original_title") val originalTitle: String,
        val overview: String,
        val popularity: Double,
        @SerialName("poster_path") val posterPath: String?,
        @SerialName("release_date") val releaseDate: String,
        val title: String,
        val video: Boolean,
        @SerialName("vote_average") val voteAverage: Double,
        @SerialName("vote_count") val voteCount: Int
    )
}

@Serializable
data class GenresResponse(
    val genres: List<Genre>
) {

    @Serializable
    data class Genre(
        val id: Int,
        val name: String
    )
}
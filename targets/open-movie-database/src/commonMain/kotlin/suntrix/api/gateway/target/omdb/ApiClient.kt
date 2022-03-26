package suntrix.api.gateway.target.omdb

import io.ktor.client.call.*
import io.ktor.client.request.*
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import suntrix.api.gateway.target.default.ApiClient

/**
 * Created by Sebastian Owodzin on 06/03/2022
 */
suspend fun ApiClient.omdb_searchMovie(title: String, releaseYear: Int, apiKey: String): MovieResponse =
    httpClient.get("https://www.omdbapi.com/") {
        url {
            parameter("t", title)
            parameter("type", "movie")
            parameter("y", releaseYear)
            parameter("plot", "full")
            parameter("apikey", apiKey)
        }
    }.body()

@Serializable
data class MovieResponse(
    @SerialName("Title") val title: String,
    @SerialName("Year") val year: String,
    @SerialName("Rated") val rated: String,
    @SerialName("Released") val released: String,
    @SerialName("Runtime") val runtime: String,
    @SerialName("Genre") val genre: String,
    @SerialName("Director") val director: String,
    @SerialName("Writer") val writer: String,
    @SerialName("Actors") val actors: String,
    @SerialName("Plot") val plot: String,
    @SerialName("Language") val language: String,
    @SerialName("Country") val country: String,
    @SerialName("Awards") val awards: String,
    @SerialName("Poster") val poster: String,
    @SerialName("Ratings") val ratings: List<Rating>,
    @SerialName("Metascore") val metascore: String,
    val imdbRating: String,
    val imdbVotes: String,
    @SerialName("imdbID") val imdbId: String,
    @SerialName("Type") val type: String,
    @SerialName("DVD") val dvd: String,
    @SerialName("BoxOffice") val boxOffice: String,
    @SerialName("Production") val production: String,
    @SerialName("Website") val website: String,
    @SerialName("Response") val response: String
) {

    @Serializable
    data class Rating(
        @SerialName("Source") val source: String,
        @SerialName("Value") val value: String,
    )
}

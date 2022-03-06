package suntrix.api.gateway.target.default

import io.ktor.client.*
import io.ktor.client.engine.*
import io.ktor.client.plugins.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.client.plugins.logging.*
import io.ktor.http.*
import io.ktor.serialization.kotlinx.json.*
import io.ktor.utils.io.core.*
import kotlinx.serialization.json.Json

/**
 * Created by Sebastian Owodzin on 06/03/2022
 */
abstract class ApiClient(
    private val baseUrl: Url,
    private val requestParams: Map<String, String>,
    private val engine: HttpClientEngine = httpClientEngine()
) : Closeable {

    private var _httpClient: HttpClient? = null
    protected val httpClient: HttpClient
        get() {
            if (_httpClient == null) {
                _httpClient = createHttpClient(engine)
            }

            return _httpClient!!
        }

    override fun close() {
        _httpClient?.close()
        _httpClient = null
    }

    private fun createHttpClient(engine: HttpClientEngine) = HttpClient(engine) {
        install(ContentNegotiation) {
            json(
                Json {
                    ignoreUnknownKeys = true
                }
            )
        }

        install(Logging) {
            level = LogLevel.INFO
        }

        BrowserUserAgent()

        defaultRequest {
            host = baseUrl.host
            contentType(ContentType.Application.Json)
            url {
                protocol = baseUrl.protocol
                requestParams.forEach {
                    parameters.append(it.key, it.value)
                }
            }
        }
    }
}

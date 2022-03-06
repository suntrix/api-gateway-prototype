package suntrix.api.gateway.target.default

import io.ktor.client.engine.*
import io.ktor.client.engine.darwin.*

/**
 * Created by Sebastian Owodzin on 22/02/2022
 */
actual fun httpClientEngine(): HttpClientEngine = Darwin.create()
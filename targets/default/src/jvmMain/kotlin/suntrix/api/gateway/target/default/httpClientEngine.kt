package suntrix.api.gateway.target.default

import io.ktor.client.engine.*
import io.ktor.client.engine.cio.*

/**
 * Created by Sebastian Owodzin on 06/03/2022
 */
actual fun httpClientEngine(): HttpClientEngine = CIO.create()
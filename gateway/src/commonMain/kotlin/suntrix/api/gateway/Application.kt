package suntrix.api.gateway

import io.ktor.server.cio.*
import io.ktor.server.engine.*
import suntrix.api.gateway.plugins.*
import suntrix.api.gateway.proxies.*

fun main() {
    embeddedServer(CIO, port = 8080, host = "0.0.0.0") {
//        configureDependencyInjection()
        configureHTTP()
        configureSerialization()
        configureRouting()

        movieProxy()
    }.start(wait = true)
}

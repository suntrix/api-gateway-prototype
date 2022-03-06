package suntrix.api.gateway.plugins

import io.ktor.server.routing.*
import io.ktor.server.application.*
import io.ktor.server.resources.*
import io.ktor.server.response.*
import io.ktor.server.routing.get

fun Application.configureRouting() {
    install(Resources) {
    }

    routing {
        get("/") {
            call.respondText("Hello World!")
        }
    }
}

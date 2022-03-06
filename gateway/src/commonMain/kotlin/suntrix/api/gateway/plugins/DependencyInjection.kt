//package suntrix.api.gateway.plugins
//
//import io.ktor.server.application.*
//import org.koin.core.Koin
//import suntrix.api.gateway.di.apiClientModule
//import suntrix.api.gateway.di.applicationModule
//
///**
// * Created by Sebastian Owodzin on 09/03/2022
// */
//fun Application.configureDependencyInjection() {
//    install(Koin) {
//        printLogger()
//        modules(
//            applicationModule(),
//            apiClientModule()
//        )
//    }
//}
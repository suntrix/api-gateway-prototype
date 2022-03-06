val junit_version: String by project
val koin_version: String by project
val kotlinx_cli: String by project
val kotlinx_coroutines_version: String by project
val kotlinx_io_version: String by project
val kotlinx_serialization_version: String by project
val ktor_version: String by project
val logback_version: String by project

plugins {
    application
    kotlin("multiplatform")
    kotlin("plugin.serialization")
    id("com.github.johnrengelman.shadow")
}

group = "suntrix.api.gateway"
version = "0.1.0"

application {
    mainClass.set("suntrix.api.gateway.ApplicationKt")
}

tasks {
    shadowJar {
        manifest {
            attributes(Pair("Main-Class", "suntrix.api.gateway.ApplicationKt"))
        }
    }
}

kotlin {
    jvm {
        compilations.all {
            kotlinOptions.jvmTarget = "1.8"
        }
        withJava()
//        testRuns["test"].executionTask.configure {
//            useJUnitPlatform()
//        }
    }

    val nativeTarget = when (System.getProperty("os.name")) {
        "Mac OS X" -> macosX64()
        "Linux" -> linuxX64 {
            compilations["main"].enableEndorsedLibs = true
        }
        else -> throw GradleException("Host OS is not supported in Kotlin/Native.")
    }

    nativeTarget.apply {
        binaries {
            executable {
                entryPoint = "suntrix.api.gateway.main"
            }
        }
    }

    sourceSets {
        all {
            languageSettings.apply {
                apiVersion = "1.6"
                languageVersion = "1.6"
                progressiveMode = true
//                optIn("io.ktor.server.locations.KtorExperimentalLocationsAPI")
            }
        }

        val commonMain by getting {
            dependencies {
                implementation(project(":targets:open-movie-database"))
                implementation(project(":targets:the-movie-db"))

//                implementation("io.insert-koin:koin-ktor:$koin_version")
                implementation("io.ktor:ktor-server-cio:$ktor_version")
                implementation("io.ktor:ktor-server-content-negotiation:$ktor_version")
                implementation("io.ktor:ktor-server-core:$ktor_version")
//                implementation("io.ktor:ktor-server-default-headers:$ktor_version")
                implementation("io.ktor:ktor-server-resources:$ktor_version")
                implementation("io.ktor:ktor-serialization-kotlinx-json:$ktor_version")
                implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:$kotlinx_coroutines_version")
            }
        }

        val commonTest by getting {
            dependencies {
                implementation(kotlin("test-common"))
                implementation(kotlin("test-annotations-common"))

                implementation("io.ktor:ktor-server-tests:$ktor_version")
            }
        }

        val jvmMain by getting {
            dependencies {
                implementation("ch.qos.logback:logback-classic:$logback_version")
            }
        }

        val jvmTest by getting {
            dependencies {
                implementation(kotlin("test-junit"))
                implementation("junit:junit:$junit_version")
            }
        }
    }
}
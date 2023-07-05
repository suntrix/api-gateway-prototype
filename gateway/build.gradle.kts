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
//        compilations.all {
//            kotlinOptions.jvmTarget = "1.8"
//        }
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
                progressiveMode = true
            }
        }

        val commonMain by getting {
            dependencies {
                implementation(project(":targets:open-movie-database"))
                implementation(project(":targets:the-movie-db"))

//                implementation("io.github.pdvrieze.xmlutil:serialization")
//                implementation("io.insert-koin:koin-ktor:$koin_version")
                implementation("io.ktor:ktor-server-cio:${Versions.KTOR}")
                implementation("io.ktor:ktor-server-content-negotiation:${Versions.KTOR}")
//                implementation("io.ktor:ktor-server-core:${Versions.KTOR}")
////                implementation("io.ktor:ktor-server-default-headers:${Versions.KTOR}")
                implementation("io.ktor:ktor-server-resources:${Versions.KTOR}")
//                implementation("io.ktor:ktor-serialization-kotlinx-json:${Versions.KTOR}")
//                implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:${Versions.Kotlinx.COROUTINES}")
            }
        }

        val commonTest by getting {
            dependencies {
                implementation(kotlin("test"))

//                implementation("io.ktor:ktor-server-tests:${Versions.KTOR}")
            }
        }

        val jvmMain by getting {
            dependencies {
//                implementation("ch.qos.logback:logback-classic:${Versions.LOGBACK}")
            }
        }

        val jvmTest by getting {
            dependencies {
//                implementation(kotlin("test-junit"))
//                implementation("junit:junit:${Versions.JUNIT}")
            }
        }
    }
}
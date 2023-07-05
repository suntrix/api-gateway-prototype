plugins {
    kotlin("multiplatform")
    kotlin("plugin.serialization")
}

kotlin {
//    val hostOs = System.getProperty("os.name")
//    val isMingwX64 = hostOs.startsWith("Windows")
//    val nativeTarget = when {
//        hostOs == "Mac OS X" -> macosX64("native")
//        hostOs == "Linux" -> linuxX64("native")
//        isMingwX64 -> mingwX64("native")
//        else -> throw GradleException("Host OS is not supported in Kotlin/Native.")
//    }

    jvm {
//        compilations.all {
//            kotlinOptions.jvmTarget = "1.8"
//        }
        withJava()
//        testRuns["test"].executionTask.configure {
//            useJUnitPlatform()
//        }
    }

    linuxX64()

    macosX64()

    sourceSets {
        all {
            languageSettings.apply {
                progressiveMode = true
//                optIn("kotlinx.serialization.ExperimentalSerializationApi")
            }
        }

        val commonMain by getting {
            dependencies {
//                implementation("com.soywiz.korlibs.krypto:krypto:$krypto_version")
//                implementation("io.ktor:ktor-client-cio:${Versions.KTOR}")
                api("io.ktor:ktor-client-core:${Versions.KTOR}")
                implementation("io.ktor:ktor-client-content-negotiation:${Versions.KTOR}")
                implementation("io.ktor:ktor-client-mock:${Versions.KTOR}")
                implementation("io.ktor:ktor-client-logging:${Versions.KTOR}")
                api("io.ktor:ktor-serialization-kotlinx-json:${Versions.KTOR}")

                implementation("org.jetbrains.kotlinx:kotlinx-datetime:${Versions.Kotlinx.DATETIME}")
//                implementation("org.jetbrains.kotlinx:kotlinx-io:$kotlinx_io_version")
                api("org.jetbrains.kotlinx:kotlinx-serialization-core:${Versions.Kotlinx.SERIALIZATION}")
                implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:${Versions.Kotlinx.SERIALIZATION}")
            }
        }

        val commonTest by getting {
            dependencies {
                implementation(kotlin("test"))

                implementation("io.ktor:ktor-client-tests:${Versions.KTOR}")
            }
        }

        val jvmMain by getting {
            dependencies {
                implementation("ch.qos.logback:logback-classic:${Versions.LOGBACK}")
                implementation("io.ktor:ktor-client-cio:${Versions.KTOR}")
            }
        }

//        val jvmTest by getting {
//            dependencies {
//                implementation(kotlin("test-junit"))
//                implementation("junit:junit:$junit_version")
//            }
//        }

        val linuxX64Main by getting {
            dependencies {
                implementation("io.ktor:ktor-client-curl:${Versions.KTOR}")
            }
        }

        val macosX64Main by getting {
            dependencies {
                implementation("io.ktor:ktor-client-darwin:${Versions.KTOR}")
//                implementation("org.jetbrains.kotlinx:kotlinx-io-native:$kotlinx_io_version")
            }
        }
    }
}

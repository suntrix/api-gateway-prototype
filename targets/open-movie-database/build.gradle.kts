val junit_version: String by project
val kotlinx_datetime_version: String by project
val kotlinx_io_version: String by project
val kotlinx_serialization_version: String by project
val krypto_version: String by project
val ktor_version: String by project
val logback_version: String by project

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
        compilations.all {
            kotlinOptions.jvmTarget = "1.8"
        }
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
                apiVersion = "1.6"
                languageVersion = "1.6"
                progressiveMode = true
                optIn("kotlinx.serialization.ExperimentalSerializationApi")
            }
        }

        val commonMain by getting {
            dependencies {
                api(project(":targets:default"))

//                implementation("com.soywiz.korlibs.krypto:krypto:$krypto_version")
//                implementation("io.ktor:ktor-client-cio:$ktor_version")
                api("io.ktor:ktor-client-core:$ktor_version")
                implementation("io.ktor:ktor-client-content-negotiation:$ktor_version")
                implementation("io.ktor:ktor-client-mock:$ktor_version")
                implementation("io.ktor:ktor-client-logging:$ktor_version")
                implementation("io.ktor:ktor-serialization-kotlinx-json:$ktor_version")

                implementation("org.jetbrains.kotlinx:kotlinx-datetime:$kotlinx_datetime_version")
//                implementation("org.jetbrains.kotlinx:kotlinx-io:$kotlinx_io_version")
                implementation("org.jetbrains.kotlinx:kotlinx-serialization-core:$kotlinx_serialization_version")
                implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:$kotlinx_serialization_version")
            }
        }

        val commonTest by getting {
            dependencies {
                implementation(kotlin("test-common"))
                implementation(kotlin("test-annotations-common"))

                implementation("io.ktor:ktor-client-tests:$ktor_version")
            }
        }

        val jvmMain by getting {
            dependencies {
                implementation("ch.qos.logback:logback-classic:$logback_version")
                implementation("io.ktor:ktor-client-cio:$ktor_version")
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
                implementation("io.ktor:ktor-client-curl:$ktor_version")
            }
        }

        val macosX64Main by getting {
            dependencies {
                implementation("io.ktor:ktor-client-darwin:$ktor_version")
//                implementation("org.jetbrains.kotlinx:kotlinx-io-native:$kotlinx_io_version")
            }
        }
    }
}

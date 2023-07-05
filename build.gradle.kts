import java.util.*

//buildscript {
//    repositories {
//        google()
//        mavenCentral()
//        maven("https://maven.pkg.jetbrains.space/kotlin/p/kotlin/dev")
//    }
//    dependencies {
//        classpath("com.guardsquare:proguard-gradle:7.2.0")
//        classpath("com.squareup.sqldelight:gradle-plugin:1.5.3")
//        classpath(kotlin("gradle-plugin", "1.6.20-RC2"))
//        classpath(kotlin("serialization", "1.6.20-RC2"))
//    }
//}

allprojects {
    repositories {
        mavenLocal()
        mavenCentral()
//        maven("https://maven.pkg.jetbrains.space/kotlin/p/kotlin/dev")
//        maven("https://maven.pkg.jetbrains.space/public/p/ktor/eap")
    }
}

subprojects {
    tasks.withType<org.jetbrains.kotlin.gradle.tasks.KotlinCompile> {
        kotlinOptions {
//            jvmTarget = "1.8"
//            jvmTarget = "17"
//            freeCompilerArgs = freeCompilerArgs + "-Xopt-in=kotlin.RequiresOptIn"
        }
    }

//    configurations.all {
//        resolutionStrategy {
//            force("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.6.0-native-mt")
//        }
//    }
}

tasks.register("clean", Delete::class) {
    delete(rootProject.buildDir)
}

plugins {
//    kotlin("multiplatform") version "1.6.20-M1-106" apply false
//    kotlin("plugin.serialization") version "1.6.20-M1-106" apply false
//    id("com.squareup.sqldelight") version "1.5.3" apply false
//    id("com.github.johnrengelman.shadow") version "8.1.1" apply false

    id("com.github.ben-manes.versions") version "0.47.0"
}

tasks.withType<com.github.benmanes.gradle.versions.updates.DependencyUpdatesTask> {

    fun isNonStable(version: String): Boolean {
        val stableKeyword = listOf("RELEASE", "FINAL", "GA").any { version.uppercase(Locale.getDefault()).contains(it) }
        val versionMatch = "^[0-9,.v-]+(-r)?$".toRegex().matches(version)

        return (stableKeyword || versionMatch).not()
    }

    rejectVersionIf {
        isNonStable(candidate.version)
    }

    checkForGradleUpdate = true
    outputFormatter = "plain,html"
    outputDir = "build/dependency-reports"
    reportfileName = "dependency-updates"
}

// check for latest dependencies - ./gradlew dependencyUpdates -Drevision=release
// check for any known dependency vulnerabilities - ./gradlew dependencyCheckAnalyze

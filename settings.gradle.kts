pluginManagement {
    repositories {
        google()
        gradlePluginPortal()
        mavenCentral()
    }
    resolutionStrategy {
        eachPlugin {
            // AGP plugin is already defined in buildSrc/build.gradle.kts

            if (requested.id.id.startsWith("org.jetbrains.kotlin")) {
                useVersion("1.8.22")
            }

            if (requested.id.id == "com.codingfeline.buildkonfig") {
                useModule("com.codingfeline.buildkonfig:buildkonfig-gradle-plugin:0.13.3")
            }

            if (requested.id.id == "com.github.johnrengelman.shadow") {
                useVersion("8.1.1")
            }

            if (requested.id.id == "org.owasp.dependencycheck") {
                useVersion("8.2.1")
            }
        }
    }
}

dependencyResolutionManagement {
    repositories {
        mavenCentral()
    }
}

rootProject.name = "api-gateway-prototype"

include(
    ":gateway",

    ":targets:default",
    ":targets:open-movie-database",
    ":targets:the-movie-db"
)
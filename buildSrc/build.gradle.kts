plugins {
    `kotlin-dsl`
}

repositories {
    google()
    mavenCentral()
}

dependencies {
    implementation(kotlin("gradle-plugin", "1.8.22"))
}

//kotlin {
//    jvmToolchain(17)
//}
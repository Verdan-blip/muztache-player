plugins {
    id("java-library")
    id(libs.plugins.kotlin.jvm.get().pluginId)
}

group = "ru.muztache.audio_player.api"

java {
    sourceCompatibility = JavaVersion.VERSION_17
    targetCompatibility = JavaVersion.VERSION_17
}

dependencies {
    implementation(project(":core-util"))
    implementation(libs.kotlin.coroutines.core)
}
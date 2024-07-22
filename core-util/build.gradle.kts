plugins {
    id("java-library")
    id(libs.plugins.kotlin.jvm.get().pluginId)
}

group = "ru.muztache.core.util"

java {
    sourceCompatibility = JavaVersion.VERSION_17
    targetCompatibility = JavaVersion.VERSION_17
}

dependencies {
    implementation(libs.kotlin.coroutines.core)
}
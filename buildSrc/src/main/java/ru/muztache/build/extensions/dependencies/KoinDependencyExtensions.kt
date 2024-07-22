package ru.muztache.build.extensions.dependencies

import org.gradle.api.Project
import org.gradle.kotlin.dsl.dependencies
import ru.muztache.build.extensions.libs
import ru.muztache.build.tools.implementation

fun Project.useKoin() {
    useKoinBase()
    useKoinCompose()
}

fun Project.useKoinBase() {
    dependencies {
        implementation(platform(libs.findLibrary("koin-bom").get()))
        implementation(libs.findBundle("koin").get())
    }
}

fun Project.useKoinCompose() {
    dependencies {
        dependencies {
            implementation(libs.findBundle("koin-compose").get())
        }
    }
}
package ru.muztache.build.extensions.dependencies

import org.gradle.api.Project
import org.gradle.kotlin.dsl.dependencies
import ru.muztache.build.extensions.libs
import ru.muztache.build.tools.implementation

fun Project.useVoyager() {
    useVoyagerBase()
}

fun Project.useVoyagerBase() {
    dependencies {
        implementation(libs.findBundle("voyager").get())
    }
}
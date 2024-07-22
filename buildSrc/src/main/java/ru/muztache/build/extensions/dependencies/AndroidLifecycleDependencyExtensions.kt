package ru.muztache.build.extensions.dependencies

import org.gradle.api.Project
import org.gradle.kotlin.dsl.dependencies
import ru.muztache.build.extensions.libs
import ru.muztache.build.tools.implementation

fun Project.useLifecycle() {
    useLifecycleBase()
    useLifecycleCompose()
}

fun Project.useLifecycleBase() {
    dependencies {
        implementation(libs.findLibrary("androidx-lifecycle-runtime-ktx").get())
        implementation(libs.findLibrary("androidx-lifecycle-viewmodel-ktx").get())
    }
}

fun Project.useLifecycleCompose() {
    dependencies {
        implementation(libs.findLibrary("androidx-lifecycle-viewmodel-compose").get())
        implementation(libs.findLibrary("androidx-lifecycle-runtime-compose").get())
    }
}
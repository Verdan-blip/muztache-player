package ru.muztache.build.extensions.dependencies

import org.gradle.api.Project
import org.gradle.kotlin.dsl.dependencies
import ru.muztache.build.extensions.android
import ru.muztache.build.extensions.libs
import ru.muztache.build.tools.androidTestImplementation
import ru.muztache.build.tools.debugImplementation
import ru.muztache.build.tools.implementation

fun Project.useCompose() {
    useComposeBase()
    useComposeUiTests()
}

fun Project.useComposeBase() {
    with(android) {
        buildFeatures.apply{
            compose = true
        }
        composeOptions.apply {
            kotlinCompilerExtensionVersion = libs.findVersion("kotlinCompilerExtension")
                .get().requiredVersion
        }
    }
    dependencies {
        implementation(platform(libs.findLibrary("androidx-compose-bom").get()))

        implementation(libs.findLibrary("androidx-activity-compose").get())
        implementation(libs.findLibrary("androidx-material3").get())
        implementation(libs.findLibrary("androidx-ui-tooling-preview").get())
        debugImplementation(libs.findLibrary("androidx-ui-tooling").get())
    }
}

fun Project.useComposeUiTests() {
    dependencies {
        androidTestImplementation(libs.findLibrary("androidx-ui-test-junit4").get())
        debugImplementation(libs.findLibrary("androidx-ui-test-manifest").get())
    }
}
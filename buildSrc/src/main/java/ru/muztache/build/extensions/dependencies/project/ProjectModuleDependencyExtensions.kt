package ru.muztache.build.extensions.dependencies.project

import org.gradle.api.Project
import org.gradle.kotlin.dsl.dependencies
import ru.muztache.build.tools.implementation

fun Project.useCoreModule() {
    dependencies {
        implementation(project(":core-common"))
        implementation(project(":core-theme"))
    }
}

fun Project.useNavigationModule() {
    dependencies {
        implementation(project(":navigation"))
    }
}


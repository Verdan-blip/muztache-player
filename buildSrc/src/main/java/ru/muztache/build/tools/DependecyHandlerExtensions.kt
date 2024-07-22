package ru.muztache.build.tools

import org.gradle.kotlin.dsl.DependencyHandlerScope

fun DependencyHandlerScope.implementation(dependency: Any) {
    add("implementation", dependency)
}

fun DependencyHandlerScope.implementation(dependencies: List<Any>) {
    dependencies.forEach { dependency ->
        implementation(dependency)
    }
}

fun DependencyHandlerScope.debugImplementation(dependency: Any) {
    add("debugImplementation", dependency)
}

fun DependencyHandlerScope.androidTestImplementation(dependency: Any) {
    add("androidTestImplementation", dependency)
}

fun DependencyHandlerScope.coreLibraryDesugaring(dependency: Any) {
    add("coreLibraryDesugaring", dependency)
}
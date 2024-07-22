package ru.muztache.build.tools

import org.gradle.api.artifacts.Dependency
import org.gradle.api.artifacts.VersionCatalog
import org.gradle.api.provider.Provider
import org.gradle.plugin.use.PluginDependency

class LibraryWithSuchAliasNotFoundException(alias: String) : Exception(alias)

class PluginWithSuchAliasNotFoundException(alias: String) : Exception(alias)

class BundleWithSuchAliasNotFoundException(alias: String) : Exception(alias)

fun VersionCatalog.requireLibrary(alias: String): Provider<out Dependency> =
    findLibrary(name).get()

fun VersionCatalog.requirePlugin(alias: String): Provider<out PluginDependency> =
    findPlugin(name).get()

fun VersionCatalog.requireBundle(alias: String): Provider<out List<Dependency>> =
    findBundle(name).get()
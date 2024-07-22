pluginManagement {
    repositories {
        google {
            content {
                includeGroupByRegex("com\\.android.*")
                includeGroupByRegex("com\\.google.*")
                includeGroupByRegex("androidx.*")
            }
        }
        mavenCentral()
        gradlePluginPortal()
    }
}
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
    }
}

rootProject.name = "muztache-player"
include(":app")
include(":feature-feed")
include(":core-common")
include(":core-theme")
include(":core-util")
include(":navigation")
include(":feature-player")
include(":feature-notch")
include(":audio-player-api")
include(":audio-player-impl")

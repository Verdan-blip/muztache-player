import ru.muztache.build.extensions.dependencies.useCompose
import ru.muztache.build.extensions.dependencies.useKoin
import ru.muztache.build.extensions.dependencies.useLifecycle
import ru.muztache.build.extensions.useCommonConfiguration
import ru.muztache.build.extensions.useViewBinding

plugins {
    id(libs.plugins.android.library.get().pluginId)
    id(libs.plugins.jetbrains.kotlin.android.get().pluginId)
}

useCommonConfiguration(name = "audio_player.impl")
useViewBinding()

useCompose()
useLifecycle()
useKoin()

dependencies {
    implementation(project(":core-common"))
    implementation(project(":core-theme"))
    implementation(project(":core-util"))
    implementation(project(":audio-player-api"))

    implementation(libs.coil)
    implementation(libs.bundles.media3)
}
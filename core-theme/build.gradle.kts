import ru.muztache.build.extensions.dependencies.useCompose
import ru.muztache.build.extensions.dependencies.useLifecycle
import ru.muztache.build.extensions.useCommonConfiguration

plugins {
    id(libs.plugins.android.library.get().pluginId)
    id(libs.plugins.jetbrains.kotlin.android.get().pluginId)
}

useCommonConfiguration("core.theme")

useCompose()
useLifecycle()

dependencies {
    implementation(libs.coil)
}
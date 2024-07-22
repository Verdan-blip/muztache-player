import ru.muztache.build.extensions.dependencies.useLifecycle
import ru.muztache.build.extensions.useCommonConfiguration

plugins {
    id(libs.plugins.android.library.get().pluginId)
    id(libs.plugins.jetbrains.kotlin.android.get().pluginId)
}

useCommonConfiguration("core.common")
useLifecycle()

dependencies {
    implementation(project(":core-util"))
}
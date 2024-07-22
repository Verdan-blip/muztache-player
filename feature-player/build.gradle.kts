import ru.muztache.build.extensions.dependencies.project.useCoreModule
import ru.muztache.build.extensions.dependencies.project.useNavigationModule
import ru.muztache.build.extensions.dependencies.useCompose
import ru.muztache.build.extensions.dependencies.useKoin
import ru.muztache.build.extensions.dependencies.useLifecycle
import ru.muztache.build.extensions.dependencies.useVoyager
import ru.muztache.build.extensions.useCommonConfiguration

plugins {
    id(libs.plugins.android.library.get().pluginId)
    id(libs.plugins.jetbrains.kotlin.android.get().pluginId)
}

useCommonConfiguration(name = "feature.player")

useCompose()
useLifecycle()
useKoin()
useVoyager()

useCoreModule()
useNavigationModule()

dependencies {

}
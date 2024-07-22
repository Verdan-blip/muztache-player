package ru.muztache.build.extensions

import com.android.build.gradle.BaseExtension
import com.android.build.gradle.LibraryExtension
import org.gradle.api.Project
import org.gradle.api.artifacts.VersionCatalog
import org.gradle.api.artifacts.VersionCatalogsExtension
import org.gradle.kotlin.dsl.dependencies
import org.gradle.kotlin.dsl.getByType
import org.gradle.kotlin.dsl.provideDelegate
import org.gradle.kotlin.dsl.withType
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile
import ru.muztache.build.config.AndroidConfig
import ru.muztache.build.config.BuildTypes
import ru.muztache.build.tools.coreLibraryDesugaring
import ru.muztache.build.tools.implementation

class NotAndroidModuleException : Exception()

val Project.android: BaseExtension
    get() = extensions.findByName("android") as? LibraryExtension
        ?: throw NotAndroidModuleException()

val Project.libs: VersionCatalog
    get() = extensions.getByType<VersionCatalogsExtension>().named("libs")

fun Project.useCommonConfiguration(
    name: String
) {
    android.apply {
        namespace = "${AndroidConfig.baseNamespace}.${name}"

        compileSdkVersion(AndroidConfig.compileSdk)

        defaultConfig.apply {
            minSdk = AndroidConfig.minSdk

            testInstrumentationRunner = AndroidConfig.unitTestsRunner
            consumerProguardFiles(AndroidConfig.consumerProguardFile)

            vectorDrawables {
                useSupportLibrary = true
            }
        }

        buildTypes {
            getByName(BuildTypes.debug) {
                isMinifyEnabled = false
            }
            getByName(BuildTypes.release) {
                isMinifyEnabled = true
                proguardFiles(
                    getDefaultProguardFile(AndroidConfig.defaultProguardFile),
                    AndroidConfig.proguardFile
                )
            }
        }

        compileOptions {
            sourceCompatibility = AndroidConfig.sourceCompatibility
            targetCompatibility = AndroidConfig.targetCompatibility
            isCoreLibraryDesugaringEnabled = true
        }

        configureKotlin()
    }

    dependencies {
        coreLibraryDesugaring(libs.findLibrary("android-desugaring-tools").get())
        implementation(libs.findBundle("android").get())
    }
}

fun Project.useViewBinding() {
    android.buildFeatures.viewBinding = true
}

fun Project.generateBuildConfig() {
    android.buildFeatures.buildConfig = true
}

private fun Project.configureKotlin() {
    tasks.withType<KotlinCompile>().configureEach {
        kotlinOptions {
            jvmTarget = AndroidConfig.targetCompatibility.toString()

            val warningsAsErrors: String? by project
            allWarningsAsErrors = warningsAsErrors.toBoolean()
            freeCompilerArgs = freeCompilerArgs + listOf(
                "-opt-in=kotlin.RequiresOptIn",
                // Enable experimental coroutines APIs, including Flow
                "-opt-in=kotlinx.coroutines.ExperimentalCoroutinesApi",
                "-opt-in=kotlinx.coroutines.FlowPreview",
            )
        }
    }
}


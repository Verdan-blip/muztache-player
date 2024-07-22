package ru.muztache.build.config

import org.gradle.api.JavaVersion

object AndroidConfig {
    const val minSdk = 28
    const val compileSdk = 34
    const val targetSdk = 34

    const val versionCode = 1
    const val versionName = "1.0"
    const val baseNamespace = "ru.muztache"
    const val applicationId = "ru.muztache"

    const val jvmTarget = "1.8"

    val sourceCompatibility = JavaVersion.VERSION_17
    val targetCompatibility = JavaVersion.VERSION_17

    val unitTestsRunner = "androidx.test.runner.AndroidJUnitRunner"

    val consumerProguardFile = "consumer-rules.pro"
    val proguardFile = "proguard-rules.pro"
    val defaultProguardFile = "proguard-android-optimize.txt"

    val excludingPackage = "/META-INF/{AL2.0,LGPL2.1}"
}
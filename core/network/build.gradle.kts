import java.util.Properties

plugins {
    alias(libs.plugins.androidLibrary)
    alias(libs.plugins.emotia.kotlin.multiplatform)
    alias(libs.plugins.kotlinxSerialization)
}

val localProperties = Properties().apply {
    val localPropertiesFile = rootProject.file("local.properties")
    if (localPropertiesFile.exists()) {
        load(localPropertiesFile.inputStream())
    }
}

android {
    namespace = "com.nexters.emotia.core.network"

    buildFeatures {
        buildConfig = true
    }

    defaultConfig {
        buildConfigField("String", "BASE_URL", "\"${localProperties.getProperty("BASE_URL")}\"")
    }
}

kotlin {
    sourceSets {
        androidMain.dependencies {
            implementation(libs.ktor.client.okhttp)
        }
        commonMain.dependencies {
            implementation(libs.ktor.client.content.negotiation)
            implementation(libs.ktor.client.core)
            implementation(libs.ktor.client.auth)
            implementation(libs.ktor.serialization.kotlinx.json)
            implementation(libs.ktor.logging)
            implementation(libs.koin.core)
            implementation(projects.core.domain.onboarding)
        }
        iosMain.dependencies {
            implementation(libs.ktor.client.darwin)
        }
    }
}

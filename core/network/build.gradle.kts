plugins {
    alias(libs.plugins.androidLibrary)
    alias(libs.plugins.emotia.kotlin.multiplatform)
    alias(libs.plugins.kotlinxSerialization)
}
android.namespace = "com.nexters.ace.core.network"

kotlin {
    sourceSets {
        androidMain.dependencies {
            implementation(libs.ktor.client.okhttp)
        }
        commonMain.dependencies {
            implementation(libs.kotlinx.serialization.json)
            implementation(libs.ktor.client.content.negotiation)
            implementation(libs.ktor.client.core)
            implementation(libs.ktor.serialization.kotlinx.json)
            implementation(libs.ktor.logging)
            implementation(libs.koin.core)
        }
        iosMain.dependencies {
            implementation(libs.ktor.client.darwin)
        }

    }
}

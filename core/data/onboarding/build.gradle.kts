plugins {
    alias(libs.plugins.androidLibrary)
    alias(libs.plugins.emotia.kotlin.multiplatform)
    alias(libs.plugins.kotlinxSerialization)
}

android.namespace = "com.nexters.emotia.core.data.onboarding"

kotlin {
    sourceSets {
        androidMain.dependencies {
            implementation(libs.ktor.client.okhttp)
        }
        commonMain.dependencies {
            implementation(libs.ktor.client.content.negotiation)
            implementation(libs.ktor.client.core)
            implementation(libs.ktor.serialization.kotlinx.json)
            implementation(libs.ktor.logging)
            implementation(libs.koin.core)

            implementation(projects.core.network)
            implementation(projects.core.domain.onboarding)

        }
        iosMain.dependencies {
            implementation(libs.ktor.client.darwin)
        }
    }
}
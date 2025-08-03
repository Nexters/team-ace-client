plugins {
    alias(libs.plugins.androidLibrary)
    alias(libs.plugins.emotia.kotlin.multiplatform)
}

kotlin {
    sourceSets {
        commonMain.dependencies {
            implementation(libs.androidx.navigation.compose)
            implementation(libs.kotlinx.serialization.core)
            implementation(libs.kotlinx.serialization.json)
        }
    }
}

android.namespace = "com.nexters.emotia.core.navigation"


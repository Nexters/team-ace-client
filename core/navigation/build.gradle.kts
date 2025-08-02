plugins {
    alias(libs.plugins.androidLibrary)
    alias(libs.plugins.ace.kotlin.multiplatform)
    alias(libs.plugins.kotlinxSerialization)
}

android {
    namespace = "org.nexters.ace.core.navigation"
}

import org.jetbrains.kotlin.gradle.plugin.mpp.KotlinNativeTarget

plugins {
    alias(libs.plugins.androidApplication)
    alias(libs.plugins.ace.kotlin.multiplatform)
    alias(libs.plugins.ace.compose.multiplatform)
}

kotlin {
    targets
        .filterIsInstance<KotlinNativeTarget>()
        .forEach { target ->
            target.binaries {
                framework {
                    baseName = "ComposeApp"
                    isStatic = true
                }
            }
        }

    sourceSets {
        androidMain.dependencies {
            implementation(compose.preview)
            implementation(libs.androidx.activity.compose)
        }
        
        commonMain.dependencies {
            implementation(projects.core.designsystem)
            implementation(projects.feature.main)
            implementation(projects.core.data)
            implementation(projects.core.domain)
            implementation(projects.core.network)
            implementation(libs.koin.core)
            implementation(libs.koin.compose)
            implementation(libs.slf4j.api)
            implementation(libs.slf4j.simple)
        }
    }
}

android {
    namespace = "com.nexters.ace"
    compileSdk = libs.versions.android.compileSdk.get().toInt()

    defaultConfig {
        applicationId = "com.nexters.ace"
        minSdk = libs.versions.android.minSdk.get().toInt()
        targetSdk = libs.versions.android.targetSdk.get().toInt()
        versionCode = 1
        versionName = "1.0"
    }

    buildTypes {
        debug {
            isDebuggable = true
        }
        getByName("debug") {
            isMinifyEnabled = false
        }
    }
}

dependencies {
    debugImplementation(compose.uiTooling)
}

import org.jetbrains.kotlin.gradle.plugin.mpp.KotlinNativeTarget

plugins {
    alias(libs.plugins.androidApplication)
    alias(libs.plugins.emotia.kotlin.multiplatform)
    alias(libs.plugins.emotia.compose.multiplatform)
    alias(libs.plugins.google.gms)
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
            implementation(project.dependencies.platform(libs.google.firebase.bom))
        }

        commonMain.dependencies {
            implementation(projects.core.designsystem)
            implementation(projects.feature.main)

            implementation(projects.core.network)
            implementation(projects.core.data.chatting)

            implementation(libs.koin.core)
            implementation(libs.koin.compose)
            implementation(libs.slf4j.api)
            implementation(libs.slf4j.simple)
            implementation(libs.androidx.lifecycle.viewmodel)
            implementation(libs.androidx.lifecycle.runtimeCompose)
        }
    }
}

android {
    namespace = "com.nexters.emotia"
    compileSdk = libs.versions.android.compileSdk.get().toInt()

    defaultConfig {
        applicationId = "com.nexters.emotia"
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

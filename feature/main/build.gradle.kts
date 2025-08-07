plugins {
    alias(libs.plugins.emotia.feature)
}

kotlin {
    sourceSets {
        commonMain.dependencies {
            implementation(projects.feature.onboarding)
            implementation(projects.feature.chatting)
            implementation(projects.feature.result)
            implementation(projects.core.domain)
            implementation(projects.core.designsystem)
            implementation(libs.koin.core)
            implementation(libs.koin.compose.viewmodel)
            implementation(libs.slf4j.api)
            implementation(libs.slf4j.android)
        }
        val nonWasmJsMain by creating {
            dependsOn(commonMain.get())
        }

        appleMain {
            dependsOn(nonWasmJsMain)
        }

        androidMain {
            dependsOn(nonWasmJsMain)
        }
    }
}

android.namespace = "com.nexters.emotia.feature.main"

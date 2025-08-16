plugins {
    alias(libs.plugins.emotia.feature)
}

kotlin {
    sourceSets {
        commonMain.dependencies {
            implementation(projects.feature.onboarding)
            implementation(projects.feature.chatting)
            implementation(projects.feature.result)
            implementation(libs.slf4j.api)
            implementation(libs.slf4j.android)
            implementation(projects.core.platform)
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

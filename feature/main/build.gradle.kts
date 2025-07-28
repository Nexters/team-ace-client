plugins {
    alias(libs.plugins.ace.feature)
}

kotlin {
    sourceSets {
        commonMain.dependencies {
            implementation(projects.core.domain)
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

android.namespace = "com.nexters.ace.feature.main"

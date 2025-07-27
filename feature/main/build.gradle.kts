plugins {
    alias(libs.plugins.ace.feature)
}

kotlin {
    sourceSets {
        commonMain.dependencies {
            implementation(projects.core.domain)
            implementation(libs.koin.core)
            implementation(libs.koin.compose.viewmodel)
            implementation("org.slf4j:slf4j-api:2.0.7")
            implementation("org.slf4j:slf4j-android:1.7.36")
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

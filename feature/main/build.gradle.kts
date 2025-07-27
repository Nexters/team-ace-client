plugins {
    alias(libs.plugins.ace.feature)
}

kotlin {
    sourceSets {
        commonMain.dependencies {

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

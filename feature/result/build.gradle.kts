plugins {
    alias(libs.plugins.emotia.feature)
}

kotlin {
    sourceSets {
        commonMain.dependencies {
            implementation(projects.core.domain)
            implementation(libs.bundles.coil)
        }
    }
}

android.namespace = "com.nexters.emotia.feature.result"

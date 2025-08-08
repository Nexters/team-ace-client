plugins {
    alias(libs.plugins.androidLibrary)
    alias(libs.plugins.emotia.kotlin.multiplatform)
}

android.namespace = "com.nexters.emotia.core.data"

kotlin {
    sourceSets {
        commonMain.dependencies {
            implementation(projects.core.domain)
            implementation(projects.core.network)
            implementation(libs.koin.core)
            implementation(libs.gitlive.firebase.config)
        }

        androidMain.dependencies {
            implementation(project.dependencies.platform(libs.google.firebase.bom))
        }
    }
}

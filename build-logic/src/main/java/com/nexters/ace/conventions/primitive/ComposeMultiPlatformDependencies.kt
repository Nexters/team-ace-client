package com.nexters.ace.conventions.primitive

import org.gradle.api.Project
import org.gradle.kotlin.dsl.configure
import org.gradle.kotlin.dsl.dependencies
import org.gradle.kotlin.dsl.getByType
import org.jetbrains.compose.ComposeExtension
import org.jetbrains.kotlin.gradle.dsl.KotlinMultiplatformExtension

internal fun Project.composeMultiplatformDependencies() {
    val composeDependencies = extensions.getByType<ComposeExtension>().dependencies
    extensions.configure<KotlinMultiplatformExtension> {
        sourceSets.apply {
            commonMain {
                dependencies {
                    implementation(composeDependencies.material3)
                    implementation(composeDependencies.runtime)
                    implementation(composeDependencies.foundation)
                    implementation(composeDependencies.ui)
                    implementation(composeDependencies.components.resources)
                    implementation(composeDependencies.components.uiToolingPreview)
                }
            }
        }
    }

    dependencies {
        "debugImplementation"(composeDependencies.uiTooling)
        "debugImplementation"(composeDependencies.preview)
    }
}

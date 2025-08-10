package com.nexters.emotia.conventions.plugin

import com.nexters.emotia.conventions.applyAndroidLibraryPlugin
import com.nexters.emotia.conventions.applyComposePlugins
import com.nexters.emotia.conventions.applyKmpPlugins
import com.nexters.emotia.conventions.applyKmpPrimitives
import com.nexters.emotia.conventions.library
import com.nexters.emotia.conventions.libs
import com.nexters.emotia.conventions.primitive.composeMultiplatformDependencies
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.configure
import org.jetbrains.kotlin.gradle.dsl.KotlinMultiplatformExtension

class EmotiaFeaturePlugin : Plugin<Project> {
    override fun apply(target: Project) = with(target) {
        applyAndroidLibraryPlugin()
        applyKmpPlugins()
        applyComposePlugins()
        applyKmpPrimitives()
        composeMultiplatformDependencies()

        extensions.configure<KotlinMultiplatformExtension> {
            sourceSets.apply {
                commonMain {
                    dependencies {
                        implementation(project(":core:designsystem"))
                        implementation(project(":core:navigation"))
                        implementation(libs.library("androidx-navigation-compose"))
                        implementation(libs.library("androidx-lifecycle-runtimeCompose"))
                        implementation(libs.library("kotlinx-immutable"))
                        implementation(libs.library("koin-core"))
                        implementation(libs.library("koin-compose"))
                        implementation(libs.library("koin-compose-viewmodel"))
                        implementation(libs.library("orbit-core"))
                        implementation(libs.library("orbit-compose"))
                        implementation(libs.library("orbit-viewmodel"))

                    }
                }
            }
        }
    }
}

package com.nexters.emotia.conventions.primitive

import com.nexters.emotia.conventions.libs
import com.nexters.emotia.conventions.library
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.configure
import org.gradle.kotlin.dsl.withType
import org.jetbrains.kotlin.gradle.dsl.JvmTarget
import org.jetbrains.kotlin.gradle.dsl.KotlinMultiplatformExtension
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile
import org.jetbrains.kotlin.gradle.tasks.KotlinNativeLink

class KotlinMultiPlatformPlugin : Plugin<Project> {
    override fun apply(target: Project) = with(target) {
        with(pluginManager) {
            apply(libs.findPlugin("kotlinMultiplatform").get().get().pluginId)
        }
        extensions.configure<KotlinMultiplatformExtension> {
            tasks.withType(KotlinCompile::class.java) {
                compilerOptions {
                    jvmTarget.set(JvmTarget.JVM_17)
                }
            }

            compilerOptions {
                freeCompilerArgs.add("-Xexpect-actual-classes")
            }

            applyDefaultHierarchyTemplate()
            tasks.withType<KotlinNativeLink>().configureEach {
                notCompatibleWithConfigurationCache("Configuration cache not supported for a system property read at configuration time")
            }

            sourceSets.apply {
                commonMain {
                    dependencies {
                        implementation(libs.library("kotlinx-serialization-json"))
                        implementation(libs.library("kotlinx-serialization-core"))
                        implementation(libs.library("kotlinx-coroutines-core"))
                    }
                }
            }
        }
    }
}

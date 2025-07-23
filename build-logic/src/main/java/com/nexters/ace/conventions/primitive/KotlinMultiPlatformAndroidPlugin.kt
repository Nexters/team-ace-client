package com.nexters.ace.conventions.primitive

import com.nexters.ace.conventions.androidExtension
import com.nexters.ace.conventions.libs
import org.gradle.api.JavaVersion
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.configure
import org.jetbrains.kotlin.gradle.dsl.JvmTarget
import org.jetbrains.kotlin.gradle.dsl.KotlinMultiplatformExtension

class KotlinMultiPlatformAndroidPlugin : Plugin<Project> {
    override fun apply(target: Project): Unit = with(target) {
        androidExtension.apply {
            compileSdk = libs.findVersion("android-compileSdk").get().requiredVersion.toInt()

            extensions.configure<KotlinMultiplatformExtension> {
                androidTarget {
                    compilerOptions.jvmTarget.set(JvmTarget.JVM_17)
                }
            }

            defaultConfig {
                minSdk = libs.findVersion("android-minSdk").get().requiredVersion.toInt()
            }

            compileOptions {
                sourceCompatibility = JavaVersion.VERSION_17
                targetCompatibility = JavaVersion.VERSION_17
            }

            packaging {
                resources {
                    excludes += "/META-INF/{AL2.0,LGPL2.1}"
                }
            }
        }
    }
}

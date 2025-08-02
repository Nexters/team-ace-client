package com.nexters.emotia.conventions.plugin

import com.nexters.emotia.conventions.applyKmpPlugins
import com.nexters.emotia.conventions.applyKmpPrimitives
import org.gradle.api.Plugin
import org.gradle.api.Project

class KotlinMultiPlatformConventionPlugin : Plugin<Project> {
    override fun apply(target: Project) = with(target) {
        applyKmpPlugins()
        applyKmpPrimitives()
    }
}

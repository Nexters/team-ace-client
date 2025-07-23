package com.nexters.ace.conventions.plugin

import com.nexters.ace.conventions.applyKmpPlugins
import com.nexters.ace.conventions.applyKmpPrimitives
import org.gradle.api.Plugin
import org.gradle.api.Project

class KotlinMultiPlatformConventionPlugin : Plugin<Project> {
    override fun apply(target: Project) = with(target) {
        applyKmpPlugins()
        applyKmpPrimitives()
    }
}

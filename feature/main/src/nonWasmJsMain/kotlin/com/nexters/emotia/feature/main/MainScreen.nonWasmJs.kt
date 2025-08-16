package com.nexters.emotia.feature.main

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.nexters.emotia.core.platform.getPlatform

@Composable
actual fun MainScreen(modifier: Modifier) {
    val platform = getPlatform()
    val deviceUuid = platform.generateDeviceUuid()
    MainContent(modifier = modifier, deviceUuid = deviceUuid)
}
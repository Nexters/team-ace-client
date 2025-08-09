package com.nexters.emotia

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import com.nexters.emotia.core.designsystem.theme.EmotiaTheme
import com.nexters.emotia.feature.main.MainScreen

@Composable
fun EmotiaApp() {
    LaunchedEffect(Unit) {
        val deviceUuid = getPlatform().generateDeviceUuid()
        // TODO: DI로 AutoLoginUseCase를 주입받아야 합니다
        // autoLoginUseCase.execute(deviceUuid)
    }
    
    EmotiaTheme {
        MainScreen()
    }
}

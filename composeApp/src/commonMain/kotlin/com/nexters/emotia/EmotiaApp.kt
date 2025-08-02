package com.nexters.emotia

import androidx.compose.runtime.Composable
import com.nexters.emotia.core.designsystem.theme.EmotiaTheme
import com.nexters.emotia.feature.main.MainScreen

@Composable
fun EmotiaApp() {
    EmotiaTheme {
        MainScreen()
    }
}

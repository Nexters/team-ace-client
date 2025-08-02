package com.nexters.emotia

import androidx.compose.runtime.Composable
import com.nexters.emotia.core.designsystem.theme.AceTheme
import com.nexters.emotia.feature.main.MainScreen

@Composable
fun AceApp() {
    AceTheme {
        MainScreen()
    }
}

package com.nexters.ace

import androidx.compose.runtime.Composable
import com.nexters.ace.core.designsystem.theme.AceTheme
import com.nexters.ace.feature.main.MainScreen

@Composable
fun AceApp() {
    AceTheme {
        MainScreen()
    }
}

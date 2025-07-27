package com.nexters.ace

import androidx.compose.runtime.Composable
import com.nexters.ace.core.designsystem.theme.AceTheme
import com.nexters.ace.feature.main.MainScreen
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
@Preview
internal fun App() {
    AceTheme {
        MainScreen()
    }
}

package com.nexters.emotia

import androidx.compose.runtime.Composable
import androidx.compose.ui.text.font.FontFamily
import com.nexters.emotia.core.designsystem.theme.EmotiaTheme
import com.nexters.emotia.feature.main.MainScreen
import emotia.composeapp.generated.resources.Res
import emotia.composeapp.generated.resources.nexon_lv2_gothic_regular
import org.jetbrains.compose.resources.Font

@Composable
fun EmotiaApp(
    fontFamily: FontFamily = FontFamily(Font(Res.font.nexon_lv2_gothic_regular)),
) {
    EmotiaTheme(
        fontFamily = fontFamily,
    ) {
        MainScreen()
    }
}

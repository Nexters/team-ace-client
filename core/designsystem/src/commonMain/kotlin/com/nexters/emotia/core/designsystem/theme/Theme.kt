package com.nexters.emotia.core.designsystem.theme

import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.text.font.FontFamily
import coil3.ColorImage
import coil3.annotation.ExperimentalCoilApi
import coil3.compose.AsyncImagePreviewHandler
import coil3.compose.LocalAsyncImagePreviewHandler

@OptIn(ExperimentalCoilApi::class)
@Composable
fun EmotiaTheme(
    fontFamily: FontFamily = FontFamily.Default,
    colors: EmotiaColors = EmotiaColors(),
    content: @Composable () -> Unit,
) {
    CompositionLocalProvider(
        LocalEmotiaColors provides colors,
        LocalEmotiaTypography provides EmotiaTypography.with(fontFamily = fontFamily),
        LocalAsyncImagePreviewHandler provides AsyncImagePreviewHandler {
            ColorImage(Color.Red.toArgb())
        },
        content = content,
    )
}

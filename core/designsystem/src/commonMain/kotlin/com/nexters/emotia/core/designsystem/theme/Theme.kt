package com.nexters.emotia.core.designsystem.theme

import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import coil3.ColorImage
import coil3.annotation.ExperimentalCoilApi
import coil3.compose.AsyncImagePreviewHandler
import coil3.compose.LocalAsyncImagePreviewHandler
import com.nexters.emotia.core.designsystem.token.EmotiaColors
import com.nexters.emotia.core.designsystem.token.EmotiaTypography
import com.nexters.emotia.core.designsystem.token.LocalEmotiaColors
import com.nexters.emotia.core.designsystem.token.LocalEmotiaTypography

@OptIn(ExperimentalCoilApi::class)
@Composable
fun EmotiaTheme(
    colors: EmotiaColors = EmotiaColors(),
    typography: EmotiaTypography = EmotiaTypography(),
    content: @Composable () -> Unit
) {
    CompositionLocalProvider(
        LocalEmotiaColors provides colors,
        LocalEmotiaTypography provides typography,
        LocalAsyncImagePreviewHandler provides AsyncImagePreviewHandler {
            ColorImage(Color.Red.toArgb())
        },
        content = content,
    )
}

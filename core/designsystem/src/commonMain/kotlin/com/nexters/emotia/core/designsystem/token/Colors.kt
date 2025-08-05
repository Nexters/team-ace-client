package com.nexters.emotia.core.designsystem.token

import androidx.compose.runtime.Immutable
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.graphics.Color

@Immutable
data class EmotiaColors(
    val green: Color = Color(0xFF5DA739),
    val darkGreen: Color = Color(0xFF235F37),
    val darkGray: Color = Color(0xFFACB7B1),

    val primaryLight: Color = Color(0xFF45A172),
    val primaryDark: Color = Color(0xFF21795E),
    val lightGray: Color = Color(0xFF999999),

    val white: Color = Color(0xFFFFFFFF),
    val white40: Color = white.copy(alpha = 0.4f),
    val black: Color = Color(0xFF000000),
    val black40: Color = black.copy(alpha = 0.4f),

    val transparent: Color = Color.Transparent,
)

val LocalEmotiaColors = staticCompositionLocalOf { EmotiaColors() }
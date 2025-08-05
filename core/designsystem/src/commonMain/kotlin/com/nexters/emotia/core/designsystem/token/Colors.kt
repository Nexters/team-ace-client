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
    val lightGray: Color = Color(0xFF999999),

    val white: Color = Color(0xFFFFFFFF),
    val black: Color = Color(0xFF000000),

    val transparent: Color = Color.Transparent,
)

val LocalEmotiaColors = staticCompositionLocalOf { EmotiaColors() }
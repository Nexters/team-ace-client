package com.nexters.emotia.core.designsystem.token

import androidx.compose.runtime.Immutable
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.graphics.Color

@Immutable
data class EmotiaColors(
    val greenLight: Color = Color(0xFF5DA739),
    val greenDark: Color = Color(0xFF235F37),
    val navyLight: Color = Color(0xFF132446),
    val navyDark: Color = Color(0xFF161D34),
    val yellowLight: Color = Color(0xFFFBA717),
    val yellowDark: Color = Color(0xFFCE7813),
    val blueLight: Color = Color(0xFF267FB6),
    val blueDark: Color = Color(0xFF0C5786),
    val pinkLight: Color = Color(0xFFE481B0),
    val pinkDark: Color = Color(0xFFA15378),

    val lightGray: Color = Color(0xFF999999),
    val darkGray: Color = Color(0xFF3C3F4B),
    val primaryLight: Color = Color(0xFF45A172),
    val primaryDark: Color = Color(0xFF21795E),
    val primaryLightGray: Color = Color(0xFFACB7B1),
    val primaryDarkGray: Color = Color(0xFF71867F),

    val white: Color = Color(0xFFFFFFFF),
    val white40: Color = white.copy(alpha = 0.4f),
    val black: Color = Color(0xFF000000),
    val black40: Color = black.copy(alpha = 0.4f),

    val transparent: Color = Color.Transparent,
)

val LocalEmotiaColors = staticCompositionLocalOf { EmotiaColors() }

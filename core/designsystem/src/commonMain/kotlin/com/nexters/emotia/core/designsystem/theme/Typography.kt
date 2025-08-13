package com.nexters.emotia.core.designsystem.theme

import androidx.compose.runtime.Immutable
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp

@Immutable
data class EmotiaTypography(
    val default: TextStyle,

    val emotia18M: TextStyle = default.copy(
        fontWeight = FontWeight.Medium,
        fontSize = 18.sp,
        lineHeight = 32.sp,
    ),
    val emotia16M: TextStyle = default.copy(
        fontWeight = FontWeight.Medium,
        fontSize = 16.sp,
        lineHeight = 26.sp,
    ),
    val emotia16R: TextStyle = default.copy(
        fontWeight = FontWeight.Normal,
        fontSize = 16.sp,
        lineHeight = 26.sp,
    ),
    val emotia14M: TextStyle = default.copy(
        fontWeight = FontWeight.Medium,
        fontSize = 14.sp,
        lineHeight = 24.sp,
    ),
    val emotia14R: TextStyle = default.copy(
        fontWeight = FontWeight.Normal,
        fontSize = 14.sp,
        lineHeight = 24.sp,
    ),
    val emotia12M: TextStyle = default.copy(
        fontWeight = FontWeight.Medium,
        fontSize = 12.sp,
        lineHeight = 18.sp,
    ),
    val emotia12R: TextStyle = default.copy(
        fontWeight = FontWeight.Normal,
        fontSize = 12.sp,
        lineHeight = 18.sp,
    ),

    ) {
    companion object {
        fun with(
            fontFamily: FontFamily = FontFamily.Default,
            fontWeight: FontWeight = FontWeight.Normal,
        ) = EmotiaTypography(
            default = TextStyle(
                fontFamily = fontFamily,
                fontWeight = fontWeight,
            ),
        )
    }
}

val LocalEmotiaTypography = staticCompositionLocalOf { EmotiaTypography.with() }

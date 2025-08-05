package com.nexters.emotia.core.designsystem.token

import androidx.compose.runtime.Immutable
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp

@Immutable
data class EmotiaTypography(
    val typoSample: TextStyle = TextStyle(
        fontSize = 32.sp,
        fontWeight = FontWeight.Bold,
        lineHeight = 40.sp,
    ),
)

val LocalEmotiaTypography = staticCompositionLocalOf { EmotiaTypography() }
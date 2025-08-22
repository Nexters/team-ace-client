package com.nexters.emotia.core.designsystem.util

import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.TextUnit

@Composable
fun Dp.toTextPx(): TextUnit = with(LocalDensity.current) { this@toTextPx.toPx().toSp() }

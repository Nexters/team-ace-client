package com.nexters.emotia.core.designsystem.component

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import kotlinx.coroutines.delay

@Composable
fun TypewriterText(
    text: String,
    modifier: Modifier = Modifier,
    textStyle: TextStyle = TextStyle.Default,
    textColor: Color = Color.Unspecified,
    typingDelayMs: Long = 50L,
    isEnabled: Boolean = true
) {
    // remember(text, isEnabled) → 같은 text면 값 유지해 백그라운드 -> 포그라운드 올라와도 재실행 안하도록
    var currentIndex by remember(text, isEnabled) { mutableStateOf(0) }

    LaunchedEffect(text, isEnabled) {
        if (!isEnabled) {
            currentIndex = text.length
            return@LaunchedEffect
        }

        currentIndex = 0
        while (currentIndex < text.length) {
            delay(typingDelayMs)
            currentIndex++
        }
    }

    Text(
        text = text.take(currentIndex),
        modifier = modifier,
        style = textStyle,
        color = textColor
    )
}
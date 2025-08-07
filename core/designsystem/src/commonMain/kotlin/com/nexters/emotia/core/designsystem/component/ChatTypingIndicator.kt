package com.nexters.emotia.core.designsystem.component

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.unit.dp
import com.nexters.emotia.core.designsystem.theme.EmotiaTheme
import kotlinx.coroutines.delay
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
fun TypingIndicator(
    modifier: Modifier = Modifier,
    dotColor: Color = Color.Gray
) {
    Row(
        modifier = modifier
            .padding(vertical = 8.dp)
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(4.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        DotAnimator(delay = 0L, dotColor = dotColor)
        DotAnimator(delay = 150L, dotColor = dotColor)
        DotAnimator(delay = 300L, dotColor = dotColor)
    }
}

@Composable
private fun DotAnimator(delay: Long, dotColor: Color) {
    val alpha = remember { Animatable(0.3f) }

    LaunchedEffect(Unit) {
        delay(delay)
        while (true) {
            alpha.animateTo(
                targetValue = 1f,
                animationSpec = tween(durationMillis = 300, easing = LinearOutSlowInEasing)
            )
            alpha.animateTo(
                targetValue = 0.3f,
                animationSpec = tween(durationMillis = 300, easing = LinearOutSlowInEasing)
            )
            delay(100L)
        }
    }

    Box(
        modifier = Modifier
            .size(8.dp)
            .graphicsLayer { this.alpha = alpha.value }
            .background(dotColor, shape = CircleShape)
    )
}


@Preview
@Composable
fun TypingIndicatorPreview() {
    EmotiaTheme {
        TypingIndicator()
    }
}
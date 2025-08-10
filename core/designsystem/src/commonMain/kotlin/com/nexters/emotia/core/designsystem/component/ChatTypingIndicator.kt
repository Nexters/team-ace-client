package com.nexters.emotia.core.designsystem.component

import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.StartOffset
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.unit.dp
import com.nexters.emotia.core.designsystem.theme.EmotiaTheme
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
fun TypingIndicator(
    modifier: Modifier = Modifier,
    dotColor: Color = Color.Gray
) {
    Row(
        modifier = modifier
            .padding(vertical = 8.dp)
            .wrapContentWidth(),
        horizontalArrangement = Arrangement.spacedBy(4.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        DotAnimator(initialDelayMillis = 0L, dotColor = dotColor)
        DotAnimator(initialDelayMillis = 150L, dotColor = dotColor)
        DotAnimator(initialDelayMillis = 300L, dotColor = dotColor)
    }
}

@Composable
private fun DotAnimator(initialDelayMillis: Long, dotColor: Color) {
    val infiniteTransition = rememberInfiniteTransition(label = "dot_alpha_transition")

    val alpha by infiniteTransition.animateFloat(
        initialValue = 0.3f,
        targetValue = 1f,
        animationSpec = infiniteRepeatable(
            animation = tween(durationMillis = 300, easing = LinearOutSlowInEasing),
            repeatMode = RepeatMode.Reverse,
            initialStartOffset = StartOffset(initialDelayMillis.toInt())
        ),
        label = "dot_alpha"
    )

    Box(
        modifier = Modifier
            .size(8.dp)
            .graphicsLayer { this.alpha = alpha }
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
package com.nexters.emotia.core.designsystem.component

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import com.nexters.emotia.core.designsystem.theme.EmotiaTheme
import com.nexters.emotia.core.designsystem.token.LocalEmotiaColors
import org.jetbrains.compose.ui.tooling.preview.Preview

enum class ChipState {
    Default,
    Pressed,
    Disabled
}

@Composable
fun EmotiaChip(
    text: String,
    mainColor: Color,
    textColor: Color,
    modifier: Modifier = Modifier,
    textStyle: TextStyle = TextStyle(),
    enabled: Boolean = true,
    onClick: () -> Unit,
    interactionSource: MutableInteractionSource = remember { MutableInteractionSource() }
) {
    val isPressed by interactionSource.collectIsPressedAsState()

    /*
     enabled = false → 사용자가 눌러도 항상 Disabled 상태
     enabled = true, 사용자가 누름 → Pressed 상태
     enabled = true, 사용자가 누르지 않음 → Default 상태
     */
    val currentState = when {
        !enabled -> ChipState.Disabled
        isPressed -> ChipState.Pressed
        else -> ChipState.Default
    }

    val backgroundColor = getChipBackgroundColor(mainColor, currentState)

    Box(
        modifier = modifier
            .clip(RoundedCornerShape(200.dp))
            .background(backgroundColor)
            .then(
                if (enabled) {
                    Modifier.clickable(
                        interactionSource = interactionSource,
                        indication = null
                    ) { onClick() }
                } else Modifier
            )
            .padding(horizontal = 16.dp, vertical = 8.dp),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = text,
            color = textColor,
            style = textStyle
        )
    }
}

data class ChipColorSet(
    val default: Color,
    val pressed: Color,
    val disabled: Color
)

@Composable
fun getChipColors(): ChipColorsProvider {
    val emotiaColors = LocalEmotiaColors.current
    return ChipColorsProvider(
        Green = ChipColorSet(
            default = emotiaColors.green,
            pressed = emotiaColors.darkGreen,
            disabled = emotiaColors.darkGray
        )
        // 추가 색상 세트들을 여기에 정의할 수 있음
        // Blue = ChipColorSet(...),
        // Red = ChipColorSet(...)
    )
}

data class ChipColorsProvider(
    val Green: ChipColorSet

)

@Composable
private fun getChipBackgroundColor(mainColor: Color, state: ChipState): Color {
    val chipColors = getChipColors()

    val colorSet = when (mainColor) {
        chipColors.Green.default -> chipColors.Green
        else -> ChipColorSet(
            default = mainColor,
            pressed = mainColor.copy(alpha = 0.8f),
            disabled = mainColor.copy(alpha = 0.4f)
        )
    }

    return when (state) {
        ChipState.Default -> colorSet.default
        ChipState.Pressed -> colorSet.pressed
        ChipState.Disabled -> colorSet.disabled
    }
}


@Preview
@Composable
fun ChipPreview() {
    EmotiaTheme {
        val chipColors = getChipColors()
        EmotiaChip(
            text = "걱정이 많았어",
            enabled = true,
            mainColor = chipColors.Green.default,
            textColor = Color.White,
            onClick = {}
        )
    }
}
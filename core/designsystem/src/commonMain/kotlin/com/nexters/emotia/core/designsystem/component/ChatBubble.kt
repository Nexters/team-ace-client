package com.nexters.emotia.core.designsystem.component

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.nexters.emotia.core.designsystem.theme.EmotiaTheme
import com.nexters.emotia.core.designsystem.theme.LocalEmotiaColors
import com.nexters.emotia.core.designsystem.theme.LocalEmotiaTypography
import org.jetbrains.compose.ui.tooling.preview.Preview

enum class BubbleType {
    MINE,
    OTHER
}

data class BubbleStyle(
    val backgroundColor: Color,
    val borderColor: Color,
    val textColor: Color,
    val textStyle: TextStyle,
)

@Composable
fun getBubbleStyle(type: BubbleType): BubbleStyle {
    val colors = LocalEmotiaColors.current
    val typography = LocalEmotiaTypography.current
    return when (type) {
        // 내가 보낸 말풍선 스타일
        BubbleType.MINE -> BubbleStyle(
            backgroundColor = colors.primaryLight,
            borderColor = colors.primaryDark,
            textColor = colors.white,
            textStyle = typography.emotia14M
        )

        // 상대방이 보낸 말풍선 스타일
        BubbleType.OTHER -> BubbleStyle(
            backgroundColor = colors.black40,
            borderColor = colors.white40,
            textColor = colors.white,
            textStyle = typography.emotia14M
        )
    }
}

@Composable
fun ChatBubbleText(
    text: String,
    type: BubbleType,
    textStyle: TextStyle,
    textColor: Color,
    modifier: Modifier = Modifier,
) {
    when (type) {
        // 상대방(AI)이 보낸 말풍선은 타이핑 애니메이션을 적용
        BubbleType.OTHER -> {
            TypewriterText(
                text = text,
                modifier = modifier,
                textStyle = textStyle,
                textColor = textColor,
                typingDelayMs = 50L,
                isEnabled = true
            )
        }

        BubbleType.MINE -> {
            Text(
                text = text,
                modifier = modifier,
                color = textColor,
                style = textStyle,
            )
        }
    }
}

fun getBubbleModifier(
    type: BubbleType,
    baseModifier: Modifier,
    screenHorizontalPadding: Dp,
    minOppositeMargin: Dp,
): Modifier {
    return when (type) {
        BubbleType.MINE -> baseModifier
            .fillMaxWidth()
            .padding(start = minOppositeMargin, end = screenHorizontalPadding)

        BubbleType.OTHER -> baseModifier
            .fillMaxWidth()
            .padding(start = screenHorizontalPadding, end = minOppositeMargin)
    }
}

@Composable
fun ChatBubble(
    text: String,
    type: BubbleType,
    modifier: Modifier = Modifier,
    cornerRadius: Dp = 8.dp,
    borderWidth: Dp = 1.dp,
    padding: Dp = 8.dp,
    screenHorizontalPadding: Dp = 8.dp,
    minOppositeMargin: Dp = 60.dp,
) {
    val style = getBubbleStyle(type)

    val bubbleModifier = getBubbleModifier(
        type = type,
        baseModifier = modifier,
        screenHorizontalPadding = screenHorizontalPadding,
        minOppositeMargin = minOppositeMargin
    )

    Box(
        modifier = bubbleModifier,
        contentAlignment = when (type) {
            BubbleType.MINE -> Alignment.CenterEnd
            BubbleType.OTHER -> Alignment.CenterStart
        }
    ) {
        Surface(
            shape = RoundedCornerShape(cornerRadius),
            color = style.backgroundColor,
            border = BorderStroke(borderWidth, style.borderColor)
        ) {
            ChatBubbleText(
                text = text,
                type = type,
                textStyle = style.textStyle,
                textColor = style.textColor,
                modifier = Modifier.padding(padding)
            )
        }
    }
}

@Preview
@Composable
fun ChatBubblePreview() {
    EmotiaTheme {
        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            ChatBubble(
                text = "졸려",
                type = BubbleType.OTHER
            )

            ChatBubble(
                text = "걱정이 많았어. 특히 장마철에는 습도가 높고 낮 최고기온이 35℃ 이상 지속되기 때문에 음식도 변질되기 쉽고 세균 번식도 활발해지면서 식중독에 의한 장염 발생률이 증가한다.",
                type = BubbleType.MINE
            )

            ChatBubble(
                text = "잠을 잘 자야 해",
                type = BubbleType.OTHER
            )
            ChatBubble(
                text = "감치왕국 ",
                type = BubbleType.MINE
            )
        }
    }
}
package com.nexters.emotia.core.designsystem.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.nexters.emotia.core.designsystem.theme.EmotiaTheme.colors
import com.nexters.emotia.core.designsystem.theme.EmotiaTheme.typography
import com.nexters.emotia.core.designsystem.util.toTextPx
import emotia.core.designsystem.generated.resources.Res
import emotia.core.designsystem.generated.resources.ic_polygon
import emotia.core.designsystem.generated.resources.img_npc
import kotlinx.coroutines.delay
import org.jetbrains.compose.resources.painterResource

/**
 * 전체 텍스트를 받아 타이핑 애니메이션을 보여주는 말풍선 컴포넌트입니다.
 * 띄어쓰기가 없으면 한 글자씩, 있으면 단어 단위로 애니메이션이 적용됩니다.
 *
 * @param fullText 표시할 전체 텍스트 문자열입니다. 이 값이 변경되면 애니메이션이 다시 시작됩니다.
 * @param useAlternativeBackground true일 경우 대체 배경 이미지를 사용합니다.
 * @param modifier Modifier
 */
@Composable
fun TypingAnimatedSpeechBubble(
    fullText: String,
    useAlternativeBackground: Boolean = false,
    modifier: Modifier = Modifier,
) {
    var displayedText by remember(fullText) { mutableStateOf("") }

    LaunchedEffect(fullText) {
        displayedText = ""
        if (fullText.contains(" ")) {
            val words = fullText.split(' ')
            var currentText = ""
            words.forEachIndexed { index, word ->
                currentText += word
                if (index < words.size - 1) {
                    currentText += " "
                }
                displayedText = currentText
                delay(150L)
            }
        } else {
            fullText.forEach { char ->
                displayedText += char
                delay(100L)
            }
        }
    }

    val boxHeight = 150.dp

    Box(
        modifier = modifier
            .fillMaxWidth()
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(boxHeight)
                .padding(start = 20.dp, end = 20.dp, bottom = 48.dp)
                .background(
                    color = colors.transparencyBlack,
                    shape = RoundedCornerShape(8.dp)
                )
                .border(
                    width = 1.dp,
                    color = colors.white40,
                    shape = RoundedCornerShape(8.dp)
                )
        )

        Image(
            painter = painterResource(Res.drawable.ic_polygon),
            contentDescription = "Polygon icon",
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .padding(end = 36.dp, bottom = 64.dp)
        )

        if (useAlternativeBackground) {
            Text(
                text = displayedText,
                color = colors.white,
                style = typography.emotia14M.copy(
                    fontSize = 14.dp.toTextPx()
                ),
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .align(Alignment.Center)
                    .offset(y = (-24).dp)
                    .padding(horizontal = 44.dp, vertical = 20.dp),
                lineHeight = 20.sp
            )
        } else {
            Image(
                painter = painterResource(Res.drawable.img_npc),
                contentDescription = "NPC character",
                modifier = Modifier
                    .align(Alignment.BottomStart)
                    .offset(x = 16.dp, y = (-70).dp)
                    .size(width = 116.dp, height = 150.dp)
            )

            Text(
                text = "요정여왕",
                color = colors.white,
                style = typography.emotia14M.copy(
                    fontSize = 14.dp.toTextPx()
                ),
                modifier = Modifier
                    .align(Alignment.BottomStart)
                    .offset(x = 40.dp, y = (-34).dp)
                    .background(
                        color = colors.greenLight,
                        shape = RoundedCornerShape(8.dp)
                    )
                    .padding(horizontal = 10.dp, vertical = 2.dp)
            )

            Text(
                text = displayedText,
                color = colors.white,
                style = typography.emotia14M.copy(
                    fontSize = 14.dp.toTextPx()
                ),
                textAlign = TextAlign.Start,
                modifier = Modifier
                    .align(Alignment.Center)
                    .offset(y = (-24).dp)
                    .padding(
                        start = 136.dp,
                        end = 44.dp,
                        top = 20.dp,
                        bottom = 16.dp
                    ),
                lineHeight = 20.sp
            )
        }
    }
}

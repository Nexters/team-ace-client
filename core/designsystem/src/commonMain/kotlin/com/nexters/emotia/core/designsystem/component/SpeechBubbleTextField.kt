package com.nexters.emotia.core.designsystem.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import emotia.core.designsystem.generated.resources.Res
import emotia.core.designsystem.generated.resources.npcTextFieldBackground
import emotia.core.designsystem.generated.resources.textFieldBackground
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
    modifier: Modifier = Modifier
) {
    var displayedText by remember(fullText) { mutableStateOf("") }

    // fullText가 변경될 때마다 애니메이션을 다시 시작합니다.
    LaunchedEffect(fullText) {
        displayedText = "" // 텍스트 초기화
        // 띄어쓰기 유무에 따라 애니메이션 방식을 다르게 처리
        if (fullText.contains(" ")) {
            // 단어 단위 애니메이션
            val words = fullText.split(' ')
            var currentText = ""
            words.forEachIndexed { index, word ->
                // 이전 텍스트에 현재 단어를 추가
                currentText += word
                // 마지막 단어가 아니면 띄어쓰기 추가
                if (index < words.size - 1) {
                    currentText += " "
                }
                displayedText = currentText
                delay(150L) // 한 단어당 딜레이
            }
        } else {
            // 글자 단위 애니메이션
            fullText.forEach { char ->
                displayedText += char
                delay(100L) // 한 글자당 딜레이 (타이핑 속도)
            }
        }
    }

    // useAlternativeBackground 값에 따라 전체 높이를 다르게 설정
    val boxHeight = if (useAlternativeBackground) 150.dp else 220.dp

    Box(
        modifier = modifier
            .fillMaxWidth()
            .height(boxHeight) // 동적으로 높이 적용
            .padding(start = 20.dp, end = 20.dp, bottom = 48.dp)
    ) {
        // useAlternativeBackground 값에 따라 배경 이미지 선택
        val backgroundImage = if (useAlternativeBackground) {
            Res.drawable.textFieldBackground
        } else {
            Res.drawable.npcTextFieldBackground
        }

        // 배경 이미지
        Image(
            painter = painterResource(backgroundImage), // 선택된 배경 이미지 리소스
            contentDescription = "Speech bubble background",
            modifier = Modifier.matchParentSize(), // Box 크기에 맞춤
            contentScale = ContentScale.FillBounds // 이미지가 잘리지 않고 꽉 차도록 설정
        )

        // 배경에 따라 텍스트 스타일(정렬, 패딩)을 다르게 적용
        val (textAlign, textModifier) = if (useAlternativeBackground) {
            // 일반 배경: 중앙 정렬, 수평 패딩
            TextAlign.Center to Modifier
                .align(Alignment.Center)
                .padding(horizontal = 24.dp, vertical = 20.dp)
        } else {
            // NPC 배경: 시작(좌측) 정렬, 시작 패딩 116dp 적용
            TextAlign.Start to Modifier
                .align(Alignment.Center)
                .padding(start = 116.dp, end = 24.dp, top = 20.dp, bottom = 16.dp)
        }

        // 타이핑되는 텍스트
        Text(
            text = displayedText,
            color = Color.White,
            style = typography.emotia14M,
            textAlign = textAlign,
            modifier = textModifier,
            lineHeight = 20.sp
        )
    }
}

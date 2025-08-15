package com.nexters.emotia.core.designsystem.component

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
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
) {
    // 1. 애니메이션 완료 상태를 저장 : rememberSaveable을 사용하여 LazyColumn의 재활용 시에도 상태를 유지
    var isAnimationFinished by rememberSaveable(text) { mutableStateOf(false) }

    // 2. 현재 표시할 글자 수를 기억 : text가 변경되면 초기화
    var currentIndex by remember(text) { mutableStateOf(0) }

    // 3. isAnimationFinished가 false일 때만 타자기 애니메이션 실행
    LaunchedEffect(text, isAnimationFinished) {
        if (isAnimationFinished) {
            currentIndex = text.length
            return@LaunchedEffect
        }

        currentIndex = 0
        while (currentIndex < text.length) {
            delay(typingDelayMs)
            currentIndex++
        }

        // 애니메이션 완료 후 상태를 true로 업데이트
        isAnimationFinished = true
    }

    Text(
        text = text.take(currentIndex),
        modifier = modifier,
        style = textStyle,
        color = textColor
    )
}
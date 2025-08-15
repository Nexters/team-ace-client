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

// 전역 애니메이션 상태 관리
object TypewriterAnimationManager {
    private val completedAnimations = mutableSetOf<String>()

    fun isAnimationCompleted(messageId: String): Boolean {
        return completedAnimations.contains(messageId)
    }

    fun markAnimationCompleted(messageId: String) {
        completedAnimations.add(messageId)
    }

}

@Composable
fun TypewriterText(
    text: String,
    modifier: Modifier = Modifier,
    textStyle: TextStyle = TextStyle.Default,
    textColor: Color = Color.Unspecified,
    typingDelayMs: Long = 50L,
    messageId: String? = null,
) {
    // messageId가 있으면 이를 key로 사용, 없으면 text를 key로 사용
    val uniqueKey = messageId ?: text

    // 전역 상태에서 애니메이션 완료 여부 확인
    val isGloballyCompleted = TypewriterAnimationManager.isAnimationCompleted(uniqueKey)

    // 현재 표시할 글자 수 - 완료된 경우 전체 텍스트 표시
    var currentIndex by remember(uniqueKey) {
        mutableStateOf(if (isGloballyCompleted) text.length else 0)
    }

    // 전역적으로 완료되지 않은 경우에만 애니메이션 실행
    LaunchedEffect(uniqueKey) {
        if (isGloballyCompleted) {
            currentIndex = text.length
            return@LaunchedEffect
        }

        currentIndex = 0
        while (currentIndex < text.length) {
            delay(typingDelayMs)
            currentIndex++
        }

        // 애니메이션 완료 후 전역 상태에 기록
        TypewriterAnimationManager.markAnimationCompleted(uniqueKey)
    }

    Text(
        text = text.take(currentIndex),
        modifier = modifier,
        style = textStyle,
        color = textColor
    )
}
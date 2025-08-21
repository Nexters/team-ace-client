package com.nexters.emotia.feature.result.result

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.nexters.emotia.core.designsystem.theme.EmotiaTheme.colors
import com.nexters.emotia.core.designsystem.theme.EmotiaTheme.typography

@Composable
fun ResultScreen(
    fairyId: Int,
    fairyName: String,
    fairyImage: String,
    contents: String,
    modifier: Modifier = Modifier,
) {
    Box(
        modifier = modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = "편지가 전송되었습니다!\n\n요정 ID: $fairyId\n요정 이름: $fairyName\n편지 내용: $contents",
            style = typography.emotia16M.copy(color = colors.white),
            textAlign = TextAlign.Center,
            modifier = Modifier.padding(24.dp)
        )
    }
}
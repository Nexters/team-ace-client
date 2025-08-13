package com.nexters.emotia.feature.chatting

import EmotiaChatTextField
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawingPadding
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.nexters.emotia.core.designsystem.component.ChatBubble
import com.nexters.emotia.core.designsystem.component.TypingIndicator
import com.nexters.emotia.core.designsystem.theme.LocalEmotiaColors
import com.nexters.emotia.feature.chatting.contract.ChattingIntent
import com.nexters.emotia.feature.chatting.contract.ChattingSideEffect
import org.koin.compose.viewmodel.koinViewModel
import org.orbitmvi.orbit.compose.collectAsState
import org.orbitmvi.orbit.compose.collectSideEffect


@Composable
fun ChattingScreen(
    onNavigateToResult: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: ChattingViewModel = koinViewModel()
) {
    val uiState by viewModel.collectAsState()
    val colors = LocalEmotiaColors.current
    val lazyListState = rememberLazyListState()

    // SideEffect 처리
    viewModel.collectSideEffect { sideEffect ->
        when (sideEffect) {
            is ChattingSideEffect.ShowError -> {
                // TODO : 에러 디자인시스템 요청
            }
        }
    }

    LaunchedEffect(uiState.messages.size) {
        if (uiState.messages.isNotEmpty()) {
            lazyListState.animateScrollToItem(uiState.messages.size - 1)
        }
    }

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(Color.Black)
            .padding(16.dp)
            .imePadding() // 키보드 패딩
            .safeDrawingPadding() // 화면 상단의 노치 등 안전 영역 패딩
    ) {
        if (uiState.isLoading && uiState.messages.isEmpty()) {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator(color = colors.primaryLight)
            }
        } else {
            LazyColumn(
                state = lazyListState,
                modifier = Modifier.weight(1f),
                contentPadding = PaddingValues(vertical = 8.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                items(uiState.messages) { message ->
                    ChatBubble(
                        text = message.text,
                        type = message.type
                    )
                }

                // 채팅 로딩 중
                if (uiState.isLoading && uiState.messages.isNotEmpty()) {
                    item {
                        Box(
                            modifier = Modifier
                                .wrapContentWidth()
                                .padding(start = 16.dp, bottom = 4.dp),
                            contentAlignment = Alignment.CenterStart
                        ) {
                            TypingIndicator(
                                modifier = Modifier
                                    .height(40.dp)
                            )
                        }
                    }
                }
            }
        }

        EmotiaChatTextField(
            value = uiState.currentInputText,
            onValueChange = { text ->
                viewModel.handleIntent(ChattingIntent.InputTextChanged(text))
            },
            onSendClick = {
                viewModel.handleIntent(ChattingIntent.SendMessage)
            },
            placeholder = "요정에게 지금 기분을 설명해보자",
            enabled = !uiState.isLoading && uiState.error == null
        )
    }
}


package com.nexters.emotia.feature.chatting

import EmotiaChatTextField
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.ime
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawingPadding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PageSize
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.ClipOp
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.clipPath
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.layout.positionInRoot
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.rememberTextMeasurer
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.Constraints
import androidx.compose.ui.unit.Density
import androidx.compose.ui.unit.dp
import com.nexters.emotia.core.designsystem.component.ChatBubble
import com.nexters.emotia.core.designsystem.component.EmotiaButton
import com.nexters.emotia.core.designsystem.component.FairyCard
import com.nexters.emotia.core.designsystem.component.TypingIndicator
import com.nexters.emotia.core.designsystem.extension.rippleClickable
import com.nexters.emotia.core.designsystem.theme.EmotiaTheme.typography
import com.nexters.emotia.core.designsystem.theme.LocalEmotiaColors
import com.nexters.emotia.core.domain.chatting.entity.Fairy
import com.nexters.emotia.feature.chatting.contract.ChattingIntent
import com.nexters.emotia.feature.chatting.contract.ChattingSideEffect
import com.nexters.emotia.feature.chatting.contract.ChattingState
import emotia.core.designsystem.generated.resources.Res
import emotia.core.designsystem.generated.resources.img_chatting_fairy
import emotia.core.designsystem.generated.resources.img_letter_background
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.ui.tooling.preview.Preview
import org.koin.compose.viewmodel.koinViewModel
import org.orbitmvi.orbit.compose.collectAsState
import org.orbitmvi.orbit.compose.collectSideEffect

@Composable
fun ChattingScreen(
    onNavigateToFairy: (fairyId: Int, fairyName: String, fairyImage: String, silhouetteImage: String, chatRoomId: Int) -> Unit,
    modifier: Modifier = Modifier,
    viewModel: ChattingViewModel = koinViewModel(),
) {
    val uiState by viewModel.collectAsState()
    val lazyListState = rememberLazyListState()
    val density = LocalDensity.current

    var isKeyboardVisible by remember { mutableStateOf(false) }
    val imeHeight = WindowInsets.ime.getBottom(density)

    LaunchedEffect(imeHeight) {
        val wasVisible = isKeyboardVisible
        val nowVisible = imeHeight > 0

        if (wasVisible != nowVisible) {
            isKeyboardVisible = nowVisible
        }
    }
    val keyboardController = LocalSoftwareKeyboardController.current
    val focusManager = LocalFocusManager.current

    var isSpotlightAnimating by remember { mutableStateOf(false) }
    var animationPhase by remember { mutableStateOf(0) }
    var fairyCardCenter by remember { mutableStateOf(Offset.Zero) }
    var fairyCardSize by remember { mutableStateOf(100f) }

    val animatedRadius by animateFloatAsState(
        targetValue = when (animationPhase) {
            0 -> 2000f // 정지 상태
            1 -> fairyCardSize * 0.7f // 1단계: 축소 (70% 크기로 더 작게)
            2 -> 0f // 2단계: 완전 차단
            else -> 2000f
        },
        animationSpec = tween(
            durationMillis = 1000,
            easing = FastOutSlowInEasing
        ),
        label = "spotlight_radius"
    )

    LaunchedEffect(isSpotlightAnimating) {
        if (isSpotlightAnimating) {
            animationPhase = 1 // 1단계: 축소 시작
            kotlinx.coroutines.delay(2000) // 1초 축소 + 1초 대기
            animationPhase = 2 // 2단계: 완전 차단
            kotlinx.coroutines.delay(1000) // 2단계 애니메이션 완료 대기

            if (uiState.fairies.isNotEmpty()) {
                val selectedIndex = uiState.selectedFairyIndex.coerceIn(0, uiState.fairies.size - 1)
                val selectedFairy = uiState.fairies.getOrNull(selectedIndex)
                selectedFairy?.let { fairy ->
                    val roomId = uiState.roomId?.toIntOrNull() ?: 0
                    onNavigateToFairy(
                        fairy.id,
                        fairy.name,
                        fairy.image,
                        fairy.silhouetteImage,
                        roomId
                    )
                }
            }
        } else {
            animationPhase = 0 // 정지 상태로 리셋
        }
    }

    viewModel.collectSideEffect { sideEffect ->
        when (sideEffect) {
            is ChattingSideEffect.ShowError -> {
                // TODO : 에러 디자인시스템 요청
            }
        }
    }

    // 새 메시지 추가 시 스크롤
    LaunchedEffect(uiState.messages.size, uiState.showFairyPager) {
        if (uiState.messages.isNotEmpty()) {
            val lastIndex = if (uiState.showFairyPager) {
                uiState.messages.size
            } else {
                uiState.messages.size - 1
            }
            lazyListState.animateScrollToItem(lastIndex)
        }
    }

    LaunchedEffect(uiState.showFairyPager) {
        if (uiState.showFairyPager) {
            keyboardController?.hide()
            focusManager.clearFocus()
        }
    }

    // 키보드 상태 변화 시 스크롤 처리 (키보드 올라갈 때만)
    LaunchedEffect(isKeyboardVisible) {
        if (uiState.messages.isNotEmpty() && isKeyboardVisible) {
            lazyListState.animateScrollToItem(uiState.messages.size - 1)
        }
    }

    Box(
        modifier = modifier
            .fillMaxSize()
            .clickable(
                interactionSource = remember { MutableInteractionSource() },
                indication = null
            ) {
                focusManager.clearFocus()
            }
    ) {
        when {
            uiState.messages.size <= 1 -> {
                // 첫 메시지
                FirstChatScreen(
                    uiState = uiState,
                    viewModel = viewModel
                )
            }

            uiState.showFairyPager -> {
                // 채팅 최대 횟수 진행 후, 요정 선택 화면
                FairySelectionScreen(
                    uiState = uiState,
                    viewModel = viewModel,
                    lazyListState = lazyListState,
                    onFairyCardPositioned = { center, size ->
                        fairyCardCenter = center
                        fairyCardSize = size
                    },
                    onSpotlightAnimationStart = { isSpotlightAnimating = true }
                )
            }

            else -> {
                // 채팅 진행 화면
                ChatConversationScreen(
                    uiState = uiState,
                    viewModel = viewModel,
                    lazyListState = lazyListState,
                    isKeyboardVisible = isKeyboardVisible
                )
            }
        }

        SpotlightOverlay(
            animationPhase = animationPhase,
            animatedRadius = animatedRadius,
            fairyCardCenter = fairyCardCenter
        )
    }
}

@Composable
private fun FirstChatScreen(
    uiState: ChattingState,
    viewModel: ChattingViewModel,
    modifier: Modifier = Modifier,
) {
    CreateRoom(
        firstMessage = uiState.firstMessage,
        currentInputText = uiState.currentInputText,
        onInputTextChange = { viewModel.handleIntent(ChattingIntent.InputTextChanged(it)) },
        onSendClick = { viewModel.handleIntent(ChattingIntent.SendMessage) },
        isLoading = uiState.isLoading,
        modifier = modifier
    )
}

@Composable
private fun ChatConversationScreen(
    uiState: ChattingState,
    viewModel: ChattingViewModel,
    lazyListState: LazyListState,
    isKeyboardVisible: Boolean,
    modifier: Modifier = Modifier,
) {
    val colors = LocalEmotiaColors.current
    val density = LocalDensity.current
    val imeHeight = WindowInsets.ime.getBottom(density)
    val textMeasurer = rememberTextMeasurer()

    // 마지막 메시지의 최종 높이 계산
    val lastMessageFinalHeight = remember(uiState.messages.lastOrNull()?.text) {
        val lastMessage = uiState.messages.lastOrNull()
        if (lastMessage != null) {
            val textLayoutResult = textMeasurer.measure(
                text = lastMessage.text,
                constraints = Constraints(
                    maxWidth = with(density) { (400.dp - 32.dp).toPx().toInt() } // 말풍선 최대 너비 - 패딩
                )
            )
            with(density) {
                (textLayoutResult.size.height + 60.dp.toPx()).toDp() // 텍스트 높이 + 말풍선 패딩
            }
        } else {
            0.dp
        }
    }

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(
                brush = Brush.verticalGradient(
                    colors = listOf(
                        colors.backgroundBlue,
                        Color.Black
                    )
                )
            )
            .padding(16.dp)
            .safeDrawingPadding()
    ) {
        if (uiState.isLoading && uiState.messages.isEmpty()) {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator(color = colors.primaryLight)
            }
        } else {
            Box(modifier = Modifier.weight(1f)) {
                LazyColumn(
                    state = lazyListState,
                    modifier = Modifier.fillMaxSize(),
                    contentPadding = PaddingValues(top = 8.dp, bottom = 8.dp),
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    itemsIndexed(
                        items = uiState.messages,
                        key = { _, message -> message.timestamp }
                    ) { index, message ->
                        ChatBubble(
                            text = message.text,
                            type = message.type,
                            messageId = message.timestamp.toString(),
                            skipTypewriterEffect = index <= 1
                        )
                    }

                    if (uiState.isLoading && uiState.messages.isNotEmpty()) {
                        item {
                            Box(
                                modifier = Modifier
                                    .wrapContentWidth()
                                    .padding(start = 16.dp, bottom = 4.dp),
                                contentAlignment = Alignment.CenterStart
                            ) {
                                TypingIndicator(
                                    modifier = Modifier.height(40.dp)
                                )
                            }
                        }
                    }

                    // 키보드 높이 / 마지막 메시지 높이 중 더 큰 값을 아래 여백으로 추가 (애니메이션에 따른 대응)
                    if (isKeyboardVisible) {
                        item {
                            val safetyMargin = 50.dp
                            val keyboardPadding = maxOf(
                                with(density) { imeHeight.toDp() },
                                lastMessageFinalHeight + safetyMargin
                            )
                            Spacer(modifier = Modifier.height(keyboardPadding))
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
            enabled = !uiState.isLoading,
            modifier = Modifier.navigationBarsPadding()
        )
    }
}

@Composable
private fun FairySelectionScreen(
    uiState: ChattingState,
    viewModel: ChattingViewModel,
    lazyListState: LazyListState,
    onFairyCardPositioned: (Offset, Float) -> Unit,
    onSpotlightAnimationStart: () -> Unit,
    modifier: Modifier = Modifier,
) {
    val colors = LocalEmotiaColors.current
    val density = LocalDensity.current

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(
                brush = Brush.verticalGradient(
                    colors = listOf(
                        colors.backgroundBlue,
                        Color.Black
                    )
                )
            )
            .padding(16.dp)
            .safeDrawingPadding()
    ) {
        LazyColumn(
            state = lazyListState,
            modifier = Modifier.weight(1f),
            contentPadding = PaddingValues(top = 8.dp, bottom = 8.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            itemsIndexed(
                items = uiState.messages,
                key = { _, message -> message.timestamp }
            ) { index, message ->
                ChatBubble(
                    text = message.text,
                    type = message.type,
                    messageId = message.timestamp.toString(),
                    skipTypewriterEffect = index <= 1
                )
            }

            item {
                FairyCardPager(
                    fairies = uiState.fairies,
                    onFairySelected = { index ->
                        viewModel.handleIntent(ChattingIntent.SelectFairy(index))
                    },
                    onFairyCardPositioned = onFairyCardPositioned,
                    density = density
                )
            }
        }

        FairySelectionBottomSection(
            selectedFairy = if (uiState.fairies.isNotEmpty()) {
                uiState.fairies.getOrNull(
                    uiState.selectedFairyIndex.coerceIn(0, uiState.fairies.size - 1)
                )
            } else {
                null
            },
            onConfirmClick = onSpotlightAnimationStart,
            onRetryClick = {
                viewModel.handleIntent(ChattingIntent.RestartChat)
            }
        )
    }
}

@Composable
private fun FairyCardPager(
    fairies: List<Fairy>,
    onFairySelected: (Int) -> Unit,
    onFairyCardPositioned: (Offset, Float) -> Unit,
    density: Density,
) {
    if (fairies.isEmpty()) return

    val pagerState = rememberPagerState(
        initialPage = if (fairies.size > 1) 1 else 0,
        pageCount = { fairies.size }
    )

    LaunchedEffect(pagerState.currentPage) {
        onFairySelected(pagerState.currentPage)
    }

    Column {
        Spacer(Modifier.height(84.dp))

        BoxWithConstraints {
            val cardWidth = 200.dp
            val horizontalPadding = maxOf(
                0.dp,
                (maxWidth - cardWidth) / 2
            )

            HorizontalPager(
                state = pagerState,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 28.dp),
                pageSize = PageSize.Fixed(cardWidth),
                pageSpacing = 24.dp,
                contentPadding = PaddingValues(horizontal = horizontalPadding)
            ) { page ->
                val fairy = fairies.getOrNull(page)
                if (fairy != null) {
                    val isSelected = page == pagerState.currentPage

                    val yOffset by animateFloatAsState(
                        targetValue = if (isSelected) 0f else 28f,
                        animationSpec = tween(
                            durationMillis = 300,
                            easing = FastOutSlowInEasing
                        )
                    )

                    FairyCard(
                        name = fairy.name,
                        image = fairy.silhouetteImage,
                        emotion = fairy.emotion,
                        emotionDescription = fairy.description,
                        isSelected = isSelected,
                        modifier = Modifier
                            .size(width = 200.dp, height = 280.dp)
                            .offset(y = yOffset.dp)
                            .then(
                                if (isSelected) {
                                    Modifier.onGloballyPositioned { coordinates ->
                                        val position = coordinates.positionInRoot()
                                        val size = coordinates.size
                                        onFairyCardPositioned(
                                            Offset(
                                                x = position.x + size.width / 2,
                                                y = position.y + size.height / 2 - with(density) { 50.dp.toPx() }
                                            ),
                                            minOf(size.width, size.height) / 2f
                                        )
                                    }
                                } else {
                                    Modifier
                                }
                            )
                    )
                }
            }
        }

        Spacer(Modifier.height(60.dp))
    }
}

@Composable
private fun FairySelectionBottomSection(
    selectedFairy: Fairy?,
    onConfirmClick: () -> Unit,
    onRetryClick: () -> Unit,
) {
    val colors = LocalEmotiaColors.current

    if (selectedFairy != null) {
        EmotiaButton(
            text = "내 감정은 ${selectedFairy.emotion}인 것 같아",
            modifier = Modifier.padding(16.dp),
            onClick = onConfirmClick
        )
    }

    Text(
        text = "다시 대화하기",
        modifier = Modifier
            .fillMaxWidth()
            .rippleClickable(onClick = onRetryClick),
        style = typography.emotia14M.copy(
            color = colors.lightGray,
            textDecoration = TextDecoration.Underline
        ),
        textAlign = TextAlign.Center
    )

    Spacer(Modifier.height(24.dp))

}

@Composable
private fun SpotlightOverlay(
    animationPhase: Int,
    animatedRadius: Float,
    fairyCardCenter: Offset,
) {
    if (animationPhase > 0) {
        Canvas(
            modifier = Modifier
                .fillMaxSize()
                .clickable(
                    indication = null,
                    interactionSource = remember { MutableInteractionSource() }
                ) {
                    // 터치 이벤트 차단
                }
        ) {
            val centerPoint = if (fairyCardCenter != Offset.Zero) {
                fairyCardCenter
            } else {
                Offset(size.width / 2, size.height / 2)
            }

            val clipPath = Path().apply {
                addOval(
                    androidx.compose.ui.geometry.Rect(
                        center = centerPoint,
                        radius = animatedRadius
                    )
                )
            }

            clipPath(clipPath, clipOp = ClipOp.Difference) {
                drawRect(color = Color.Black, size = size)
            }
        }
    }
}

@Composable
private fun CreateRoom(
    firstMessage: String,
    currentInputText: String,
    onInputTextChange: (String) -> Unit,
    onSendClick: () -> Unit,
    isLoading: Boolean,
    modifier: Modifier = Modifier,
) {
    Box(
        modifier = modifier
            .fillMaxSize()
            .safeDrawingPadding()
            .imePadding()
    ) {
        Column(
            modifier = Modifier.fillMaxSize()
        ) {
            // 배경 이미지 영역
            Box(
                modifier = Modifier.fillMaxWidth()

            ) {
                Image(
                    painter = painterResource(Res.drawable.img_letter_background),
                    contentDescription = null,
                    modifier = Modifier.fillMaxWidth(),
                    contentScale = ContentScale.Crop
                )

                // 요정 이미지를 고정 위치에 배치
                Box(
                    modifier = Modifier
                        .align(Alignment.Center)
                        .offset(y = 30.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Image(
                        painter = painterResource(Res.drawable.img_chatting_fairy),
                        contentDescription = null,
                        modifier = Modifier.size(72.dp),
                        contentScale = ContentScale.Fit
                    )
                }

                if (firstMessage.isNotEmpty()) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 50.dp)
                            .wrapContentHeight()
                            .align(Alignment.Center)
                            .offset(y = (-50).dp), // 요정을 고정시키고 메시지 뜨도록
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Box(
                            modifier = Modifier
                                .background(
                                    Color(0xE62D2B38),
                                    shape = androidx.compose.foundation.shape.RoundedCornerShape(8.dp)
                                )
                                .padding(horizontal = 12.dp, vertical = 7.dp)
                        ) {
                            Text(
                                text = firstMessage,
                                color = Color.White,
                                style = typography.emotia12M,
                                textAlign = TextAlign.Center
                            )
                        }
                    }
                }
            }

            Spacer(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f)
                    .background(
                        Brush.verticalGradient(
                            colors = listOf(
                                Color(0xFF171E2D),
                                Color(0xFF1A1A1B)
                            )
                        )
                    )
                    .navigationBarsPadding(),
            )
        }

        EmotiaChatTextField(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(horizontal = 16.dp, vertical = 24.dp)
                .navigationBarsPadding(),
            value = currentInputText,
            onValueChange = onInputTextChange,
            onSendClick = onSendClick,
            placeholder = "요정에게 지금 기분을 설명해보자",
            enabled = !isLoading && firstMessage.isNotEmpty()
        )
    }
}

@Preview
@Composable
fun ChattingScreenPreview() {
    CreateRoom(
        firstMessage = "안녕하세요! 오늘 기분은 어떤가요?",
        currentInputText = "",
        onInputTextChange = {},
        onSendClick = {},
        isLoading = false
    )
}

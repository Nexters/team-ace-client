package com.nexters.emotia.feature.chatting

import EmotiaChatTextField
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.ime
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawingPadding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.pager.HorizontalPager
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
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import com.nexters.emotia.core.designsystem.component.ChatBubble
import com.nexters.emotia.core.designsystem.component.EmotiaButton
import com.nexters.emotia.core.designsystem.component.FairyCard
import com.nexters.emotia.core.designsystem.component.TypingIndicator
import com.nexters.emotia.core.designsystem.theme.EmotiaTheme.typography
import com.nexters.emotia.core.designsystem.theme.LocalEmotiaColors
import com.nexters.emotia.feature.chatting.contract.ChattingIntent
import com.nexters.emotia.feature.chatting.contract.ChattingSideEffect
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
    val colors = LocalEmotiaColors.current
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
            1 -> fairyCardSize // 1단계: 축소
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
                val selectedFairy = uiState.fairies[uiState.selectedFairyIndex.coerceIn(
                    0,
                    uiState.fairies.size - 1
                )]
                onNavigateToFairy(
                    selectedFairy.id,
                    selectedFairy.name,
                    selectedFairy.image,
                    selectedFairy.silhouetteImage,
                    uiState.roomId?.toInt() ?: 0
                )
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
        modifier = modifier.fillMaxSize()
    ) {
        if (uiState.messages.size <= 1) {
            // 첫 채팅 입력 화면
            CreateRoom(
                firstMessage = uiState.firstMessage,
                currentInputText = uiState.currentInputText,
                onInputTextChange = { text ->
                    viewModel.handleIntent(
                        ChattingIntent.InputTextChanged(text)
                    )
                },
                onSendClick = {
                    viewModel.handleIntent(ChattingIntent.SendMessage)
                },
                isLoading = uiState.isLoading
            )
        } else {
            // 기존 채팅 화면
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
                        contentPadding = PaddingValues(
                            top = 8.dp,
                            bottom = if (isKeyboardVisible) {
                                // 키보드가 올라왔을 때 키보드 높이만큼 bottom padding 추가
                                with(density) { imeHeight.toDp() / 2 }
                            } else {
                                8.dp
                            }
                        ),
                        verticalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        items(
                            items = uiState.messages,
                            key = { message -> message.timestamp }
                        ) { message ->
                            ChatBubble(
                                text = message.text,
                                type = message.type,
                                messageId = message.timestamp.toString()
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

                        item {
                            val showFairyCards =
                                uiState.showFairyPager && uiState.fairies.isNotEmpty()
                            AnimatedVisibility(
                                visible = showFairyCards,
                                enter = slideInVertically(
                                    initialOffsetY = { it },
                                    animationSpec = tween(
                                        durationMillis = 500,
                                        easing = FastOutSlowInEasing
                                    )
                                ),
                                exit = slideOutVertically(
                                    targetOffsetY = { it },
                                    animationSpec = tween(durationMillis = 300)
                                )
                            ) {
                                val pagerState = rememberPagerState(
                                    initialPage = if (uiState.fairies.size > 1) 1 else 0,
                                    pageCount = { uiState.fairies.size }
                                )

                                LaunchedEffect(pagerState.currentPage) {
                                    viewModel.handleIntent(
                                        ChattingIntent.SelectFairy(
                                            pagerState.currentPage
                                        )
                                    )
                                }

                                Spacer(Modifier.height(84.dp))

                                HorizontalPager(
                                    state = pagerState,
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(vertical = 28.dp),
                                    contentPadding = PaddingValues(horizontal = 80.dp),
                                    pageSpacing = 24.dp
                                ) { page ->
                                    val fairy = uiState.fairies[page]
                                    val isSelected =
                                        page == pagerState.currentPage

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
                                            .size(
                                                width = 200.dp,
                                                height = 280.dp
                                            )
                                            .offset(y = yOffset.dp)
                                            .then(
                                                if (isSelected) {
                                                    Modifier.onGloballyPositioned { coordinates ->
                                                        val position =
                                                            coordinates.positionInRoot()
                                                        val size =
                                                            coordinates.size
                                                        fairyCardCenter =
                                                            Offset(
                                                                x = position.x + size.width / 2,
                                                                y = position.y + size.height / 2 - with(
                                                                    density
                                                                ) { 50.dp.toPx() }
                                                            )
                                                        fairyCardSize =
                                                            minOf(
                                                                size.width,
                                                                size.height
                                                            ) / 2f
                                                    }
                                                } else {
                                                    Modifier
                                                }
                                            )
                                    )
                                }

                                Spacer(Modifier.height(60.dp))
                            }
                        }
                    }
                }

                if (uiState.showFairyPager && uiState.fairies.isNotEmpty()) {
                    val selectedIndex = uiState.selectedFairyIndex.coerceIn(
                        0,
                        uiState.fairies.size - 1
                    )

                    EmotiaButton(
                        text = "내 감정은 ${uiState.fairies[selectedIndex].emotion}이야",
                        modifier = Modifier.padding(16.dp),
                        onClick = {
                            isSpotlightAnimating = true
                        }
                    )

                    Text(
                        text = "다시 대화하기",
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable {
                                // TODO : 채팅 내비게이션 다시 시작 구현
                            },
                        style = typography.emotia14M.copy(
                            color = colors.lightGray,
                            textDecoration = TextDecoration.Underline
                        ),
                        textAlign = TextAlign.Center
                    )

                    Spacer(Modifier.height(24.dp))
                } else {
                    EmotiaChatTextField(
                        value = uiState.currentInputText,
                        onValueChange = { text ->
                            viewModel.handleIntent(
                                ChattingIntent.InputTextChanged(
                                    text
                                )
                            )
                        },
                        onSendClick = {
                            viewModel.handleIntent(ChattingIntent.SendMessage)
                        },
                        placeholder = "요정에게 지금 기분을 설명해보자",
                        enabled = !uiState.isLoading && uiState.error == null
                    )
                }
            }
        }

        if (animationPhase > 0) {
            Canvas(
                modifier = Modifier
                    .fillMaxSize()
                    .clickable(
                        indication = null,
                        interactionSource = remember { MutableInteractionSource() }
                    ) {
                        // 터치 이벤트 차단 (빈 람다)
                    }
            ) {
                val centerPoint =
                    if (fairyCardCenter != Offset.Zero) fairyCardCenter else Offset(
                        size.width / 2,
                        size.height / 2
                    )

                val clipPath = Path().apply {
                    addOval(
                        androidx.compose.ui.geometry.Rect(
                            center = centerPoint,
                            radius = animatedRadius
                        )
                    )
                }

                clipPath(clipPath, clipOp = ClipOp.Difference) {
                    drawRect(
                        color = Color.Black,
                        size = size
                    )
                }
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
        modifier = modifier.fillMaxSize()
    ) {
        Column(
            modifier = Modifier.fillMaxSize()
        ) {
            // 배경 이미지 영역
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentHeight()
            ) {
                Image(
                    painter = painterResource(Res.drawable.img_letter_background),
                    contentDescription = null,
                    modifier = Modifier.fillMaxWidth(),
                    contentScale = ContentScale.Crop
                )

                Column(
                    modifier = Modifier
                        .align(Alignment.Center)
                        .padding(horizontal = 50.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    if (firstMessage.isNotEmpty()) {
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
                        Spacer(modifier = Modifier.height(12.dp))
                    }

                    // Fairy 이미지
                    Box(
                        modifier = Modifier.size(72.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        Image(
                            painter = painterResource(Res.drawable.img_chatting_fairy),
                            contentDescription = null,
                            modifier = Modifier.fillMaxSize(),
                            contentScale = ContentScale.Fit
                        )
                    }
                }
            }

            Spacer(
                modifier = Modifier
                    .fillMaxSize()
                    .background(
                        Brush.verticalGradient(
                            colors = listOf(
                                Color(0xFF171E2D),
                                Color(0xFF1A1A1B)
                            )
                        )
                    )
            )
        }

        Column(
            modifier = Modifier
                .fillMaxSize()
                .safeDrawingPadding()
                .imePadding()
        ) {
            Spacer(modifier = Modifier.weight(1f))

            Box(
                modifier = Modifier.padding(horizontal = 16.dp, vertical = 24.dp)
            ) {
                EmotiaChatTextField(
                    value = currentInputText,
                    onValueChange = onInputTextChange,
                    onSendClick = onSendClick,
                    placeholder = "요정에게 지금 기분을 설명해보자",
                    enabled = !isLoading && firstMessage.isNotEmpty()
                )
            }
        }
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

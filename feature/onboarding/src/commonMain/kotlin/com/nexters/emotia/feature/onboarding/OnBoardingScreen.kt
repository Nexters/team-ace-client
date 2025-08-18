package com.nexters.emotia.feature.onboarding

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.nexters.emotia.core.designsystem.component.TypingAnimatedSpeechBubble
import emotia.core.designsystem.generated.resources.Res
import emotia.core.designsystem.generated.resources.eight
import emotia.core.designsystem.generated.resources.first
import emotia.core.designsystem.generated.resources.five
import emotia.core.designsystem.generated.resources.four
import emotia.core.designsystem.generated.resources.nine
import emotia.core.designsystem.generated.resources.second
import emotia.core.designsystem.generated.resources.seven
import emotia.core.designsystem.generated.resources.six
import emotia.core.designsystem.generated.resources.ten
import emotia.core.designsystem.generated.resources.third
import org.jetbrains.compose.resources.painterResource
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun OnBoardingScreen(
    viewModel: OnBoardingViewModel = koinViewModel(),
    onOnboardingFinished: () -> Unit,
    modifier: Modifier = Modifier,
    deviceUuid: String
) {
    val uiState by viewModel.uiState.collectAsState()
    var startStory by remember { mutableStateOf(false) }

    LaunchedEffect(Unit) {
        viewModel.performAutoLogin(deviceUuid)
    }

    LaunchedEffect(uiState.isLoginSuccess) {
        if (uiState.isLoginSuccess) {
            startStory = true
        }
    }

    Box(
        modifier = modifier
            .fillMaxSize()
            .background(Color.Black),
        contentAlignment = Alignment.Center
    ) {
        if (!startStory) {
            LoginContent(
                isLoading = uiState.isLoading,
                errorMessage = uiState.errorMessage,
                onRetry = { viewModel.performAutoLogin(deviceUuid) }
            )
        } else {
            StoryContent(
                onOnboardingFinished = onOnboardingFinished
            )
        }
    }
}

@Composable
private fun LoginContent(
    isLoading: Boolean,
    errorMessage: String?,
    onRetry: () -> Unit
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        if (errorMessage != null) {
            Text(
                text = "오류: $errorMessage",
                color = Color.White,
                modifier = Modifier.padding(bottom = 16.dp)
            )
            Button(onClick = onRetry) {
                Text("다시 시도")
            }
        } else {
            CircularProgressIndicator(color = Color.White)
            Text(
                text = "로그인 중...",
                color = Color.White,
                modifier = Modifier.padding(top = 16.dp)
            )
        }
    }
}

@Composable
private fun StoryContent(
    onOnboardingFinished: () -> Unit
) {
    val totalSteps = 12
    var currentStep by remember { mutableStateOf(0) }

    BoxWithConstraints(
        modifier = Modifier
            .fillMaxSize()
            .clickable(
                interactionSource = remember { MutableInteractionSource() },
                indication = null
            ) {
                if (currentStep < totalSteps - 1) {
                    currentStep++
                } else {
                    onOnboardingFinished()
                }
            }
    ) {
        val screenHeight = this.maxHeight

        // --- 애니메이션 상태 값 정의 ---
        val animationSpecFloat = tween<Float>(durationMillis = 600)
        val animationSpecIntOffset = tween<IntOffset>(durationMillis = 600)

        // first 이미지 애니메이션 (위로 이동 및 크기 축소)
        val firstImageOffsetY by animateDpAsState(
            targetValue = if (currentStep >= 1) -(screenHeight / 4) else 0.dp,
            animationSpec = tween(600)
        )
        val firstImageHeight by animateDpAsState(
            targetValue = if (currentStep >= 1) screenHeight / 2 else screenHeight,
            animationSpec = tween(600)
        )

        // second 이미지 애니메이션 (위로 이동)
        val secondImageOffsetY by animateDpAsState(
            targetValue = if (currentStep == 2) -(screenHeight / 2) else 0.dp,
            animationSpec = tween(600)
        )

        // --- 각 스텝 별 UI 구성 ---

        // Step 1 & 2: first 이미지
        AnimatedVisibility(
            visible = currentStep <= 1, // Step 1과 2에서만 보임
            exit = fadeOut(animationSpecFloat)
        ) {
            Image(
                painter = painterResource(Res.drawable.first),
                contentDescription = "First screen",
                modifier = Modifier
                    .fillMaxWidth()
                    .height(firstImageHeight)
                    .align(Alignment.Center)
                    .offset(y = firstImageOffsetY),
                contentScale = ContentScale.Crop
            )
        }

        // Step 2: second 이미지 (하단)
        AnimatedVisibility(
            visible = currentStep == 1, // Step 2에서만 보임
            enter = slideInVertically(animationSpecIntOffset) { it / 2 } + fadeIn(animationSpecFloat),
            exit = fadeOut(animationSpecFloat)
        ) {
            Image(
                painter = painterResource(Res.drawable.second),
                contentDescription = "Second screen",
                modifier = Modifier
                    .fillMaxWidth()
                    .height(screenHeight / 2)
                    .align(Alignment.BottomCenter),
                contentScale = ContentScale.Crop
            )
        }

// Step 3: third(하단) + second(상단) 스택
        AnimatedVisibility(
            visible = currentStep == 2, // Step 3에서만 보임
            enter = fadeIn(animationSpecFloat),
            exit = fadeOut(animationSpecFloat)
        ) {
            Column(modifier = Modifier.fillMaxSize()) {
                // 상단 절반 - second 이미지 (투명도 50%)
                Image(
                    painter = painterResource(Res.drawable.second),
                    contentDescription = "Second screen (top half)",
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f), // 절반 차지
                    contentScale = ContentScale.Crop,
                    alpha = 0.5f
                )

                // 하단 절반 - third 이미지
                Image(
                    painter = painterResource(Res.drawable.third),
                    contentDescription = "Third screen (bottom half)",
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f), // 절반 차지
                    contentScale = ContentScale.Crop
                )
            }
        }


        // Step 4: four 이미지로 전환
        AnimatedVisibility(
            visible = currentStep == 3,
            enter = fadeIn(animationSpecFloat),
            exit = fadeOut(animationSpecFloat)
        ) {
            Image(
                painter = painterResource(Res.drawable.four),
                contentDescription = "Fourth screen",
                modifier = Modifier.fillMaxSize(),
                contentScale = ContentScale.Crop
            )
        }

        // Step 4
        AnimatedVisibility(
            visible = currentStep == 4,
            enter = fadeIn(animationSpecFloat),
            exit = fadeOut(animationSpecFloat)
        ) {
            Image(
                painter = painterResource(Res.drawable.five),
                contentDescription = "Fifth screen",
                modifier = Modifier.fillMaxSize(),
                contentScale = ContentScale.Crop
            )
        }

        // Step 5
        AnimatedVisibility(
            visible = currentStep == 5,
            enter = fadeIn(animationSpecFloat),
            exit = fadeOut(animationSpecFloat)
        ) {
            Image(
                painter = painterResource(Res.drawable.six),
                contentDescription = "Fifth screen",
                modifier = Modifier.fillMaxSize(),
                contentScale = ContentScale.Crop
            )
        }
        // Step 6
        AnimatedVisibility(
            visible = currentStep == 6,
            enter = fadeIn(animationSpecFloat),
            exit = fadeOut(animationSpecFloat)
        ) {
            Image(
                painter = painterResource(Res.drawable.six),
                contentDescription = "Fifth screen",
                modifier = Modifier.fillMaxSize(),
                contentScale = ContentScale.Crop
            )
        }

        // Step 7
        AnimatedVisibility(
            visible = currentStep == 7,
            enter = fadeIn(animationSpecFloat),
            exit = fadeOut(animationSpecFloat)
        ) {
            Image(
                painter = painterResource(Res.drawable.seven),
                contentDescription = "Fifth screen",
                modifier = Modifier.fillMaxSize(),
                contentScale = ContentScale.Crop
            )
        }

        AnimatedVisibility(
            visible = currentStep == 8,
            enter = fadeIn(animationSpecFloat),
            exit = fadeOut(animationSpecFloat)
        ) {
            Image(
                painter = painterResource(Res.drawable.eight),
                contentDescription = "Sixth screen",
                modifier = Modifier.fillMaxSize(),
                contentScale = ContentScale.Crop
            )
        }

        AnimatedVisibility(
            visible = currentStep == 9,
            enter = fadeIn(animationSpecFloat),
            exit = fadeOut(animationSpecFloat)
        ) {
            Image(
                painter = painterResource(Res.drawable.nine),
                contentDescription = "Sixth screen",
                modifier = Modifier.fillMaxSize(),
                contentScale = ContentScale.Crop
            )
        }

        AnimatedVisibility(
            visible = currentStep == 10,
            enter = fadeIn(animationSpecFloat),
            exit = fadeOut(animationSpecFloat)
        ) {
            Image(
                painter = painterResource(Res.drawable.ten),
                contentDescription = "Sixth screen",
                modifier = Modifier.fillMaxSize(),
                contentScale = ContentScale.Crop
            )
        }

        AnimatedVisibility(
            visible = currentStep == 11,
            enter = fadeIn(animationSpecFloat),
            exit = fadeOut(animationSpecFloat)
        ) {
            Image(
                painter = painterResource(Res.drawable.ten),
                contentDescription = "Sixth screen",
                modifier = Modifier.fillMaxSize(),
                contentScale = ContentScale.Crop
            )
        }

        // 말풍선
        val (speechBubbleText, useAlternativeBackground) = when (currentStep) {
            0 -> "깊은 숲 너머,\n오랜 시간 잊혀진 세계가 있어요." to true
            2 -> "이곳은 이모티아.\n감정을 품은 요정들과 생명들이 살아가는 숲." to true
            3 -> "...어라?\n저기, 나무 뒤에... 누군가 있는 것 같아요." to true
            4 -> "감정 요정들은 쉽게 모습을 드러내지 않아요.\n부끄러움을 많이 타거든요." to true
            5 -> "앗..! 드디어 만났네.\n네 감정이 이 숲에 닿았을 때부터, 조용히 지켜보고 있었어." to false
            6 -> "나는 감정 요정들이 사는 숲, 이모티아의 여왕이야." to false
            7 -> "기쁨, 슬픔, 분노, 외로움… 당신이 느끼는 모든 감정이 이 숲 어딘가에 숨겨져 있어요." to false
            8 -> "이 숲을 거닐면서, 감정 요정들을 함께 찾아보자." to false
            9 -> "그 아이들을 만나면, 너의 마음도 조금씩 또렷해질 거야." to false
            10 -> "자, 이제 이모티아의 숲으로 같이 걸어가 볼까?" to false
            else -> null to false
        }

        if (speechBubbleText != null) {
            AnimatedVisibility(
                visible = true,
                enter = fadeIn(tween(300)),
                exit = fadeOut(tween(300)),
                modifier = Modifier.align(Alignment.BottomCenter)
            ) {
                // 커스텀 말풍선 컴포넌트 호출 및 파라미터 전달
                TypingAnimatedSpeechBubble(
                    fullText = speechBubbleText,
                    useAlternativeBackground = useAlternativeBackground
                )
            }
        }

        // Step 12: 마지막 화면
        AnimatedVisibility(
            visible = currentStep == 11,
            enter = fadeIn(animationSpecFloat)
        ) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color.Black), // 전체 배경 어둡게
                contentAlignment = Alignment.Center
            ) {
                // 원형 뷰포트
                Box(
                    modifier = Modifier
                        .size(150.dp) // 원 크기
                        .clip(CircleShape) // 동그랗게 자름
                        .background(Color.Transparent) // 원 안은 배경이 그대로 보이도록
                ) {
                    Image(
                        painter = painterResource(Res.drawable.ten), // 보여줄 이미지
                        contentDescription = "마지막 배경",
                        modifier = Modifier.fillMaxSize(),
                        contentScale = ContentScale.Crop
                    )
                }

                // 텍스트 오버레이
                Text(
                    "이제 당신의 감정을 기록해 보세요",
                    color = Color.White,
                    fontSize = 20.sp,
                    modifier = Modifier
                        .align(Alignment.BottomCenter)
                        .padding(bottom = 100.dp)
                )
            }
        }


        // 진행 상태 텍스트 (우상단)
        if (currentStep < totalSteps - 1) {
            Text(
                text = "${currentStep + 1} / $totalSteps",
                color = Color.White,
                fontSize = 14.sp,
                modifier = Modifier
                    .align(Alignment.TopEnd)
                    .padding(top = 60.dp, end = 24.dp)
            )
        }
    }
}

@Composable
private fun SpeechBubble(
    text: String,
    modifier: Modifier = Modifier
) {
    Surface(
        modifier = modifier.padding(start = 24.dp, end = 24.dp, bottom = 100.dp),
        color = Color.Black.copy(alpha = 0.7f),
        shape = RoundedCornerShape(16.dp)
    ) {
        Text(
            text = text,
            color = Color.White,
            fontSize = 16.sp,
            textAlign = TextAlign.Center,
            modifier = Modifier.padding(horizontal = 24.dp, vertical = 16.dp),
            lineHeight = 24.sp
        )
    }
}

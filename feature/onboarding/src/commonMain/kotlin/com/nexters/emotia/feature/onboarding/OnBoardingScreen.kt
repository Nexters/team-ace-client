package com.nexters.emotia.feature.onboarding

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun OnBoardingScreen(
    viewModel: OnBoardingViewModel = koinViewModel(),
    // 온보딩이 모두 끝났을 때 호출될 람다
    onOnboardingFinished: () -> Unit,
    modifier: Modifier = Modifier,
    deviceUuid: String
) {
    val uiState by viewModel.uiState.collectAsState()

    // 1. 스토리 온보딩을 시작할지 결정하는 상태 변수
    var startStory by remember { mutableStateOf(false) }

    // 로그인 로직은 그대로 유지
    LaunchedEffect(Unit) {
        println("[OnBoardingScreen] 화면 진입 - 자동 로그인 시작, UUID: $deviceUuid")
        viewModel.performAutoLogin(deviceUuid)
    }

    // 2. 로그인에 성공하면, 화면을 전환하는 대신 'startStory' 상태를 true로 변경
    LaunchedEffect(uiState.isLoginSuccess) {
        if (uiState.isLoginSuccess) {
            println("[OnBoardingScreen] 로그인 성공, 스토리 온보딩 시작")
            startStory = true
        }
    }

    Box(
        modifier = modifier
            .fillMaxSize()
            .background(Color.Black), // 전체 배경을 검은색으로 설정
        contentAlignment = Alignment.Center
    ) {
        // 3. 'startStory' 값에 따라 로그인 UI 또는 스토리 UI를 보여줌
        if (!startStory) {
            // --- 로그인 UI ---
            LoginContent(
                isLoading = uiState.isLoading,
                errorMessage = uiState.errorMessage,
                onRetry = { viewModel.performAutoLogin(deviceUuid) }
            )
        } else {
            // --- 스토리 온보딩 UI ---
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
    val totalImages = 6
    var currentImageIndex by remember { mutableStateOf(0) }

    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        // 배경 이미지들 (fadeIn/fadeOut 애니메이션)
        repeat(totalImages) { index ->
            AnimatedVisibility(
                visible = currentImageIndex == index,
                enter = fadeIn(animationSpec = tween(800)),
                exit = fadeOut(animationSpec = tween(800))
            ) {
                OnboardingImagePlaceholder(
                    imageIndex = index,
                    modifier = Modifier.fillMaxSize()
                )
            }
        }

        // 클릭 영역 (전체 화면)
        Box(
            modifier = Modifier
                .fillMaxSize()
                .clickable(
                    interactionSource = remember { MutableInteractionSource() },
                    indication = null
                ) {
                    if (currentImageIndex < totalImages - 1) {
                        currentImageIndex++
                    } else {
                        onOnboardingFinished()
                    }
                }
        )

        // 진행 상태 텍스트 (우상단)
        Text(
            text = "${currentImageIndex + 1} / $totalImages",
            color = Color.White,
            fontSize = 14.sp,
            modifier = Modifier
                .align(Alignment.TopEnd)
                .padding(top = 60.dp, end = 24.dp)
        )
    }
}

@Composable
private fun OnboardingImagePlaceholder(
    imageIndex: Int,
    modifier: Modifier = Modifier
) {
    // 실제 이미지가 있으면 사용, 없으면 컬러 플레이스홀더 사용
    Box(
        modifier = modifier.background(
            color = when (imageIndex) {
                0 -> Color(0xFF1A1A2E)
                1 -> Color(0xFF1A1A2E)
                2 -> Color(0xFF1A1A2E)
                3 -> Color(0xFF1A1A2E)
                4 -> Color(0xFF1A1A2E)
                5 -> Color(0xFF1A1A2E)
                else -> Color(0xFF1A1A2E)
            }
        ),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = when (imageIndex) {
                    0 -> "🌟"
                    1 -> "😊"
                    2 -> "🧘‍♀️"
                    3 -> "💪"
                    4 -> "🎯"
                    5 -> "🎉"
                    else -> "😊"
                },
                fontSize = 80.sp
            )
            Text(
                text = when (imageIndex) {
                    0 -> "감치왕국에\n오신 걸 환영합니다"
                    1 -> "감정을 이해하고\n표현해보세요"
                    2 -> "마음의 평온을\n찾아보세요"
                    3 -> "건강한 감정 관리\n습관을 만들어보세요"
                    4 -> "목표를 설정하고\n달성해보세요"
                    5 -> "새로운 시작을\n축하합니다"
                    else -> "온보딩 ${imageIndex + 1}"
                },
                color = Color.White,
                fontSize = 24.sp,
                modifier = Modifier.padding(top = 24.dp),
                textAlign = TextAlign.Center
            )
        }
    }
}

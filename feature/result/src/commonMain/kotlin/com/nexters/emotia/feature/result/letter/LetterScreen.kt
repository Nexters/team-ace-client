package com.nexters.emotia.feature.result.letter

import EmotiaMultiLineTextField
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.ime
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import coil3.compose.LocalPlatformContext
import coil3.compose.SubcomposeAsyncImage
import coil3.request.ImageRequest
import coil3.request.crossfade
import com.nexters.emotia.core.designsystem.component.EmotiaButton
import com.nexters.emotia.core.designsystem.theme.EmotiaTheme.colors
import com.nexters.emotia.core.designsystem.theme.EmotiaTheme.typography
import com.nexters.emotia.feature.result.letter.contract.LetterIntent
import com.nexters.emotia.feature.result.letter.contract.LetterSideEffect
import emotia.core.designsystem.generated.resources.Res
import emotia.core.designsystem.generated.resources.img_letter_background
import org.jetbrains.compose.resources.painterResource
import org.koin.compose.viewmodel.koinViewModel
import org.orbitmvi.orbit.compose.collectAsState
import org.orbitmvi.orbit.compose.collectSideEffect

@Composable
fun LetterScreen(
    fairyId: Int,
    fairyName: String,
    fairyImage: String,
    chatRoomId: Int,
    onNavigateToResult: (String, String) -> Unit,
    onNavigateToChatting: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: LetterViewModel = koinViewModel(),
) {
    val uiState by viewModel.collectAsState()
    var isKeyboardVisible by remember { mutableStateOf(false) }
    val scrollState = rememberScrollState()

    LaunchedEffect(fairyId, fairyName, fairyImage, chatRoomId) {
        viewModel.handleIntent(
            LetterIntent.InitializeFairy(
                fairyId,
                fairyName,
                fairyImage,
                chatRoomId
            )
        )
    }

    viewModel.collectSideEffect { sideEffect ->
        when (sideEffect) {
            is LetterSideEffect.NavigateToResult -> {
                onNavigateToResult(
                    sideEffect.letter.name,
                    sideEffect.letter.contents
                )
            }

            is LetterSideEffect.NavigateToChatting -> onNavigateToChatting()
            is LetterSideEffect.ShowError -> {
                // TODO: 에러 처리
            }
        }
    }

    val density = LocalDensity.current
    val imeHeight = WindowInsets.ime.getBottom(density)

    LaunchedEffect(imeHeight) {
        val wasVisible = isKeyboardVisible
        val nowVisible = imeHeight > 0

        if (wasVisible != nowVisible) {
            isKeyboardVisible = nowVisible
        }
    }

    LaunchedEffect(isKeyboardVisible) {
        if (isKeyboardVisible) {
            scrollState.animateScrollTo(scrollState.maxValue)
        } else {
            scrollState.animateScrollTo(0)
        }
    }

    Column(
        modifier = modifier
            .fillMaxSize()
            .navigationBarsPadding()
            .verticalScroll(scrollState),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Box {
            Image(
                painter = painterResource(Res.drawable.img_letter_background),
                contentDescription = "Background Image",
                modifier = Modifier.fillMaxWidth(),
                contentScale = ContentScale.FillWidth
            )

            SubcomposeAsyncImage(
                model = ImageRequest.Builder(LocalPlatformContext.current)
                    .data(fairyImage)
                    .crossfade(true)
                    .build(),
                contentDescription = "$fairyImage image",
                contentScale = ContentScale.Inside,
                modifier = Modifier
                    .size(108.dp)
                    .align(Alignment.BottomCenter)
                    .offset(y = (-49).dp)
                    .clip(RoundedCornerShape(16.dp)),
                error = {
                    // TODO : 에러 이미지 처리
                },
            )
        }

        Column(
            modifier = Modifier
                .let { modifier ->
                    if (isKeyboardVisible) {
                        modifier.fillMaxWidth()
                    } else {
                        modifier.weight(1f).fillMaxWidth()
                    }
                }
                .background(
                    brush = Brush.verticalGradient(
                        colors = listOf(
                            Color(0xFF171E2D),
                            Color(0xFF1A1A1B)
                        )
                    )
                ),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.height(28.dp))

            EmotiaMultiLineTextField(
                value = uiState.contents,
                onValueChange = { viewModel.handleIntent(LetterIntent.UpdateContents(it)) },
                placeholder = "${fairyName}에게 위로의 말을 건네보자.",
                modifier = Modifier
                    .let { modifier ->
                        if (isKeyboardVisible) {
                            modifier.heightIn(min = 160.dp)
                        } else {
                            modifier.weight(1f)
                        }
                    }
                    .fillMaxWidth()
                    .padding(horizontal = 24.dp)
            )

            Spacer(modifier = Modifier.height(32.dp))

            EmotiaButton(
                text = "위로 건네기",
                onClick = {
                    viewModel.handleIntent(LetterIntent.SendLetter)
                },
                enabled = uiState.contents.isNotBlank() && !uiState.isLoading,
                modifier = Modifier.padding(horizontal = 24.dp)
            )

            Spacer(modifier = Modifier.height(16.dp))

            Text(
                text = "다시 대화하기",
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable {
                        onNavigateToChatting()
                    },
                style = typography.emotia14M.copy(
                    color = colors.lightGray,
                    textDecoration = TextDecoration.Underline
                ),
                textAlign = TextAlign.Center
            )

            Spacer(modifier = Modifier.height(24.dp))

            if (isKeyboardVisible) {
                Spacer(modifier = Modifier.height(210.dp))
            }
        }

        // TODO : 로딩이 빨라서 보류. 다른 정책 필요
//        // 로딩 오버레이
//        if (uiState.isLoading) {
//            Box(
//                modifier = Modifier
//                    .fillMaxSize()
//                    .background(Color.Black.copy(alpha = 0.5f))
//                    .clickable { /* 터치 차단 */ },
//                contentAlignment = Alignment.Center
//            ) {
//                CircularProgressIndicator(
//                    color = colors.primaryDark
//                )
//            }
//        }
    }
}


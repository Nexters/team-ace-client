package com.nexters.emotia.feature.result.fairy

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.ClipOp
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.clipPath
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import coil3.compose.LocalPlatformContext
import coil3.compose.SubcomposeAsyncImage
import coil3.request.ImageRequest
import coil3.request.crossfade
import com.nexters.emotia.core.designsystem.component.TypingAnimatedSpeechBubble
import com.nexters.emotia.core.designsystem.theme.EmotiaTheme.colors
import com.nexters.emotia.feature.result.fairy.contract.FairyIntent
import com.nexters.emotia.feature.result.fairy.contract.FairySideEffect
import emotia.core.designsystem.generated.resources.Res
import emotia.core.designsystem.generated.resources.img_letter_background
import org.jetbrains.compose.resources.painterResource
import org.koin.compose.viewmodel.koinViewModel
import org.orbitmvi.orbit.compose.collectAsState
import org.orbitmvi.orbit.compose.collectSideEffect

@Composable
fun FairyScreen(
    fairyId: Int,
    fairyName: String,
    fairyImage: String,
    onNavigateToOnBoarding: () -> Unit,
    onNavigateToChatting: () -> Unit,
    onNavigateToLetter: (Int, String, String) -> Unit,
    modifier: Modifier = Modifier,
    viewModel: FairyViewModel = koinViewModel(),
) {
    val uiState by viewModel.collectAsState()
    var currentStep by remember { mutableStateOf(0) }

    LaunchedEffect(fairyId, fairyName, fairyImage) {
        viewModel.handleIntent(FairyIntent.InitializeFairy(fairyId, fairyName, fairyImage))
        viewModel.handleIntent(FairyIntent.StartExpandAnimation)
    }

    viewModel.collectSideEffect { sideEffect ->
        when (sideEffect) {
            is FairySideEffect.NavigateToOnBoarding -> onNavigateToOnBoarding()
            is FairySideEffect.NavigateToChatting -> onNavigateToChatting()
            is FairySideEffect.ShowError -> {
                // TODO: 에러 처리
            }
        }
    }

    val animatedRadius by animateFloatAsState(
        targetValue = if (uiState.isExpanding) 2000f else 0f,
        animationSpec = tween(
            durationMillis = 1000,
            easing = FastOutSlowInEasing
        ),
        label = "expand_radius"
    )

    val stepTexts = when (currentStep) {
        0 -> "앗...!"
        1 -> "저 아이는... ${fairyName}이야!"
        2 -> "${fairyName}에게 위로를 건네볼까?"
        else -> null
    }

    Box(
        modifier = modifier
            .fillMaxSize()
            .background(colors.black)
            .navigationBarsPadding()
            .clickable(
                interactionSource = remember { MutableInteractionSource() },
                indication = null
            ) {
                if (animatedRadius >= 1800f) {
                    if (currentStep < 2) {
                        currentStep++
                    } else {
                        onNavigateToLetter(uiState.fairyId, uiState.fairyName, uiState.fairyImage)
                    }
                }
            }
    ) {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.TopCenter
        ) {
            Column(
                modifier = Modifier
                    .widthIn(max = 500.dp)
                    .fillMaxWidth()
            ) {
                Box(modifier = Modifier.fillMaxWidth()) {
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

                Box(
                    modifier = Modifier
                        .weight(1f)
                        .fillMaxWidth()
                        .background(
                            brush = Brush.verticalGradient(
                                colors = listOf(
                                    Color(0xFF171E2D),
                                    Color(0xFF1A1A1B)
                                )
                            )
                        )
                )
            }

            if (stepTexts != null && animatedRadius >= 1800f) {
                Box(
                    modifier = Modifier
                        .widthIn(max = 500.dp)
                        .align(Alignment.BottomCenter)
                ) {
                    AnimatedVisibility(
                        visible = true,
                        enter = fadeIn(tween(300)),
                        exit = fadeOut(tween(300))
                    ) {
                        TypingAnimatedSpeechBubble(
                            fullText = stepTexts,
                            useAlternativeBackground = false
                        )
                    }
                }
            }
        }

        if (animatedRadius < 1800f) {
            Canvas(
                modifier = Modifier
                    .fillMaxSize()
                    .clickable(
                        indication = null,
                        interactionSource = remember { MutableInteractionSource() }
                    ) {
                        // 애니메이션 중 터치 차단
                    }
            ) {
                val centerPoint = Offset(size.width / 2, size.height / 2)
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
}


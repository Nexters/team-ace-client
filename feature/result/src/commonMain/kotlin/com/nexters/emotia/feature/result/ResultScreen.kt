package com.nexters.emotia.feature.result

import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
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
import com.nexters.emotia.feature.result.contract.ResultIntent
import com.nexters.emotia.feature.result.contract.ResultSideEffect
import emotia.core.designsystem.generated.resources.Res
import emotia.core.designsystem.generated.resources.img_result_background
import org.jetbrains.compose.resources.painterResource
import org.koin.compose.viewmodel.koinViewModel
import org.orbitmvi.orbit.compose.collectAsState
import org.orbitmvi.orbit.compose.collectSideEffect

@Composable
fun ResultScreen(
    fairyId: Int,
    fairyName: String,
    fairyImage: String,
    onNavigateToOnBoarding: () -> Unit,
    onNavigateToChatting: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: ResultViewModel = koinViewModel(),
) {
    val uiState by viewModel.collectAsState()

    LaunchedEffect(fairyId, fairyName, fairyImage) {
        viewModel.handleIntent(ResultIntent.InitializeFairy(fairyId, fairyName, fairyImage))
        viewModel.handleIntent(ResultIntent.StartExpandAnimation)
    }

    viewModel.collectSideEffect { sideEffect ->
        when (sideEffect) {
            is ResultSideEffect.NavigateToOnBoarding -> onNavigateToOnBoarding()
            is ResultSideEffect.NavigateToChatting -> onNavigateToChatting()
            is ResultSideEffect.ShowError -> {
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

    Box(
        modifier = modifier.fillMaxSize()
    ) {

        Image(
            painter = painterResource(Res.drawable.img_result_background),
            contentDescription = "Background Image",
            modifier = Modifier.fillMaxHeight(),
            contentScale = ContentScale.Crop
        )

        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            SubcomposeAsyncImage(
                model = ImageRequest.Builder(LocalPlatformContext.current)
                    .data(uiState.fairyImage)
                    .crossfade(true)
                    .build(),
                contentDescription = "${uiState.fairyName} image",
                contentScale = ContentScale.Inside,
                modifier = Modifier
                    .size(300.dp)
                    .clip(RoundedCornerShape(16.dp)),
                error = {
                    // TODO : 에러 이미지 처리
                },
            )

            Spacer(Modifier.height(50.dp))
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

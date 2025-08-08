package com.nexters.emotia.feature.result

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PageSize
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.nexters.emotia.core.designsystem.component.NetworkImage
import com.nexters.emotia.domain.chat.entity.Fairy
import org.koin.compose.viewmodel.koinViewModel
import kotlin.math.abs
import kotlin.math.min

@Composable
fun ResultScreen(
    chatRoomId: String,
    onNavigateToOnBoarding: () -> Unit,
    onNavigateToChatting: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: ResultViewModel = koinViewModel(),
) {
    LaunchedEffect(chatRoomId) {
        viewModel.loadFairies(chatRoomId)
    }

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Text(
            text = "감치왕국 화이팅",
            style = MaterialTheme.typography.headlineMedium,
            modifier = Modifier.padding(top = 30.dp, bottom = 100.dp)
        )

        when {
            viewModel.uiState.isLoading -> {
                Box(
                    modifier = Modifier.weight(1f),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator()
                }
            }

            viewModel.uiState.error != null -> {
                Box(
                    modifier = Modifier.weight(1f),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = viewModel.uiState.error!!,
                        color = MaterialTheme.colorScheme.error,
                        style = MaterialTheme.typography.bodyMedium
                    )
                }
            }

            viewModel.uiState.fairies.isNotEmpty() -> {
                // API 응답 데이터 로깅
                LaunchedEffect(viewModel.uiState.fairies) {
                    println("🧚‍♀️ Received ${viewModel.uiState.fairies.size} fairies:")
                    viewModel.uiState.fairies.forEachIndexed { index, fairy ->
                        println("  [$index] name: '${fairy.name}', image: '${fairy.image}', emotion: '${fairy.emotion}'")
                    }
                }

                FairyCardPager(
                    fairies = viewModel.uiState.fairies,
                    modifier = Modifier.weight(1f)
                )
            }

            else -> {
                Box(
                    modifier = Modifier.weight(1f),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "요정 정보가 없습니다.",
                        style = MaterialTheme.typography.bodyMedium
                    )
                }
            }
        }

        OutlinedButton(onClick = onNavigateToOnBoarding) {
            Text("처음으로")
        }
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
private fun FairyCardPager(
    fairies: List<Fairy>,
    modifier: Modifier = Modifier,
) {
    val pagerState = rememberPagerState(
        initialPage = min(1, fairies.lastIndex), // 두 번째 카드부터 중앙 시작(데이터가 1개면 0)
        initialPageOffsetFraction = 0f
    ) { fairies.size }

    val density = LocalDensity.current
    val sidePeek = 32.dp
    val pageGap = 16.dp

    BoxWithConstraints(modifier = modifier.fillMaxWidth()) {
        val pageWidth = maxWidth - sidePeek * 2

        HorizontalPager(
            state = pagerState,
            pageSize = PageSize.Fixed(pageWidth),
            pageSpacing = pageGap,
            contentPadding = PaddingValues(horizontal = sidePeek)
        ) { page ->
            val fairy = fairies[page]

            // 0f(중앙) ~ 1f(양 끝)로 정규화된 오프셋
            val rawOffset = (page - pagerState.currentPage) + pagerState.currentPageOffsetFraction
            val proximity = 1f - min(1f, abs(rawOffset))

            val scale = 0.9f + 0.15f * proximity // 0.9 ~ 1.05
            val liftPx = with(density) { (-20).dp.toPx() * proximity }

            Box(
                modifier = Modifier
                    .width(pageWidth)
                    .graphicsLayer {
                        scaleX = scale
                        scaleY = scale
                        translationY = liftPx
                    }
            ) {
                FairyCard(
                    fairy = fairy,
                    isCenter = page == pagerState.currentPage,
                    modifier = Modifier.width(pageWidth)
                )
            }
        }
    }
}

@Composable
private fun FairyCard(
    fairy: Fairy,
    isCenter: Boolean = false,
    modifier: Modifier = Modifier,
) {
    Card(
        modifier = modifier
            .height(320.dp),
        shape = RoundedCornerShape(20.dp),
        elevation = CardDefaults.cardElevation(
            defaultElevation = if (isCenter) 16.dp else 4.dp
        ),
        colors = CardDefaults.cardColors(
            containerColor = if (isCenter)
                MaterialTheme.colorScheme.surface
            else
                MaterialTheme.colorScheme.surface.copy(alpha = 0.7f)
        )
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Text(
                text = fairy.name,
                style = MaterialTheme.typography.headlineSmall.copy(
                    fontWeight = FontWeight.Bold
                ),
                textAlign = TextAlign.Center,
                color = if (isCenter)
                    MaterialTheme.colorScheme.onSurface
                else
                    MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f)
            )

            Box(
                modifier = Modifier
                    .size(160.dp)
                    .background(
                        color = MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.2f),
                        shape = RoundedCornerShape(80.dp)
                    ),
                contentAlignment = Alignment.Center
            ) {
                val imageUrl = fairy.image

                // 이미지 URL 로깅
                LaunchedEffect(imageUrl) {
                    println("🖼️ Loading image URL: $imageUrl")
                    println("🧚 Fairy image field: '${fairy.image}'")
                }

                NetworkImage(
                    imageUrl = imageUrl,
                    contentDescription = fairy.name,
                    modifier = Modifier.size(140.dp),
                    contentScale = ContentScale.Fit
                )
            }

            Text(
                text = fairy.emotion,
                style = MaterialTheme.typography.titleLarge.copy(
                    fontWeight = FontWeight.SemiBold,
                    color = MaterialTheme.colorScheme.primary
                ),
                textAlign = TextAlign.Center
            )
        }
    }
}

package com.nexters.emotia.feature.result

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import coil3.compose.LocalPlatformContext
import coil3.compose.SubcomposeAsyncImage
import coil3.request.ImageRequest
import coil3.request.crossfade
import emotia.core.designsystem.generated.resources.Res
import emotia.core.designsystem.generated.resources.img_letter_background
import org.jetbrains.compose.resources.painterResource

@Composable
fun LetterScreen(
    fairyId: Int,
    fairyName: String,
    fairyImage: String,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            painter = painterResource(Res.drawable.img_letter_background),
            contentDescription = "Background Image",
            modifier = Modifier.fillMaxWidth(),
            contentScale = ContentScale.Crop
        )
        Column(
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
                ),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            SubcomposeAsyncImage(
                model = ImageRequest.Builder(LocalPlatformContext.current)
                    .data(fairyImage)
                    .crossfade(true)
                    .build(),
                contentDescription = "$fairyImage image",
                contentScale = ContentScale.Inside,
                modifier = Modifier
                    .size(108.dp)
                    .offset(y = (-164).dp)
                    .clip(RoundedCornerShape(16.dp)),
                error = {
                    // TODO : 에러 이미지 처리
                },
            )
        }
    }
}

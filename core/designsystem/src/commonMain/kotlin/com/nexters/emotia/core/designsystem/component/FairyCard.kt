package com.nexters.emotia.core.designsystem.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import coil3.compose.LocalPlatformContext
import coil3.compose.SubcomposeAsyncImage
import coil3.request.ImageRequest
import coil3.request.crossfade
import com.nexters.emotia.core.designsystem.theme.EmotiaTheme
import com.nexters.emotia.core.designsystem.theme.EmotiaTheme.colors
import com.nexters.emotia.core.designsystem.theme.EmotiaTheme.typography
import emotia.core.designsystem.generated.resources.Res
import emotia.core.designsystem.generated.resources.img_divider_above
import emotia.core.designsystem.generated.resources.img_divider_below
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
fun FairyCard(
    name: String,
    image: String,
    emotion: String,
    modifier: Modifier = Modifier,
) {
    val shape = RoundedCornerShape(12.dp)
    val innerShape = RoundedCornerShape(6.dp)

    val borderBrush = Brush.verticalGradient(
        colors = listOf(colors.primaryLight, colors.primaryDark)
    )
    val cardBackground = colors.transparencyBlack
    val panelBackground = Color(0xFF161821)

    Box(
        modifier = modifier
            .shadow(elevation = 10.dp, shape = shape)
            .border(width = 5.dp, brush = borderBrush, shape = shape)
            .clip(shape)
            .background(cardBackground)
            .padding(12.dp)
    ) {
        Column(
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(140.dp)
                    .clip(innerShape)
                    .background(panelBackground),
                contentAlignment = Alignment.Center
            ) {
                SubcomposeAsyncImage(
                    model = ImageRequest.Builder(LocalPlatformContext.current)
                        .data(image)
                        .crossfade(true)
                        .build(),
                    contentDescription = "$name image",
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(140.dp)
                        .clip(innerShape),
                    loading = {
                        // TODO : 스켈레톤?
                        CircularProgressIndicator()
                    },
                    error = {
                        // TODO : 에러 이미지 처리
                    },
                )
            }

            Spacer(Modifier.height(12.dp))

            Text(
                text = name,
                style = typography.emotia18M.copy(
                    color = colors.white
                )
            )

            Spacer(Modifier.height(4.dp))

            Image(
                painter = painterResource(Res.drawable.img_divider_above),
                contentDescription = "Divider",
            )

            Spacer(Modifier.height(6.dp))

            Text(
                text = emotion,
                style = typography.emotia12R.copy(
                    color = colors.primaryLight,
                )
            )

            Spacer(Modifier.height(6.dp))

            Image(
                painter = painterResource(Res.drawable.img_divider_below),
                contentDescription = "Divider",
            )
        }
    }
}

@Preview
@Composable
fun FairyCardPreview() {
    EmotiaTheme {
        FairyCard(
            name = "행복한 요정",
            image = "",
            emotion = "행복",
            modifier = Modifier.width(200.dp)
        )
    }
}

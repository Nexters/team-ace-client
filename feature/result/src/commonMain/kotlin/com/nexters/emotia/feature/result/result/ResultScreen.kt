package com.nexters.emotia.feature.result.result

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import com.nexters.emotia.core.designsystem.component.EmotiaButton
import com.nexters.emotia.core.designsystem.theme.EmotiaTheme.colors
import com.nexters.emotia.core.designsystem.theme.EmotiaTheme.typography
import emotia.core.designsystem.generated.resources.Res
import emotia.core.designsystem.generated.resources.img_divider_above
import emotia.core.designsystem.generated.resources.img_divider_below
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
fun ResultScreen(
    fairyName: String,
    contents: String,
    modifier: Modifier = Modifier,
    onNavigateToOnBoarding: () -> Unit = {},
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .navigationBarsPadding()
            .background(
                brush = Brush.verticalGradient(
                    colors = listOf(
                        colors.backgroundBlue,
                        Color.Black
                    )
                )
            ),
        verticalArrangement = Arrangement.Center
    ) {

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 30.dp)
                .background(color = colors.transparencyBlack, shape = RoundedCornerShape(16.dp))
                .padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Text(
                text = buildAnnotatedString {
                    withStyle(style = SpanStyle(color = colors.white)) {
                        append("너의 ")
                    }
                    withStyle(style = SpanStyle(color = colors.pinkLight)) {
                        append(fairyName)
                    }
                    withStyle(style = SpanStyle(color = colors.white)) {
                        append("에게 보내는 위로")
                    }
                },
                style = typography.emotia18M
            )

            Spacer(modifier = Modifier.height(24.dp))

            Image(
                painter = painterResource(Res.drawable.img_divider_above),
                contentDescription = "Divider",
            )

            Spacer(modifier = Modifier.height(24.dp))

            Text(
                text = contents,
                style = typography.emotia16R,
                textAlign = TextAlign.Center,
                color = colors.white
            )

            Spacer(modifier = Modifier.height(24.dp))

            Image(
                painter = painterResource(Res.drawable.img_divider_below),
                contentDescription = "Divider",
            )

            Spacer(modifier = Modifier.height(24.dp))

            Text(
                text = "- 너의 마음 속 ${fairyName}으로부터",
                textAlign = TextAlign.Center,
                style = typography.emotia14M,
                color = colors.lightGray
            )
        }

        Spacer(modifier = Modifier.height(40.dp))

        EmotiaButton(
            text = "홈 화면으로",
            onClick = onNavigateToOnBoarding,
            modifier = Modifier.fillMaxWidth().padding(horizontal = 24.dp)
        )

        Spacer(modifier = Modifier.height(16.dp))

        EmotiaButton(
            text = "도감 보기 (준비 중)",
            enabled = false,
            onClick = {},
            modifier = Modifier.fillMaxWidth().padding(horizontal = 24.dp)
        )
    }
}

@Preview
@Composable
fun ResultScreenPreview() {
    ResultScreen(
        fairyName = "행복한 요정",
        contents = "이 위로의 글은 너의 마음을 달래줄 것이야.",
        modifier = Modifier
    )
}


package com.nexters.emotia.core.designsystem.component

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.nexters.emotia.core.designsystem.theme.EmotiaTheme.colors
import com.nexters.emotia.core.designsystem.theme.EmotiaTheme.typography
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
fun EmotiaButton(
    text: String,
    enabled: Boolean = true,
    modifier: Modifier = Modifier,
    onClick: () -> Unit = {},
) {
    // TODO : 리플 색 반영 및 enable 분기처리 보강
    Button(
        onClick = {
            if (enabled) onClick()
        },
        modifier = modifier.fillMaxWidth(),
        shape = RoundedCornerShape(12.dp),
        colors = ButtonDefaults.buttonColors(
            containerColor = if (enabled) colors.primaryLight else colors.lightGray,
        ),
        contentPadding = PaddingValues(16.dp)
    ) {
        Text(
            text = text,
            style = typography.emotia18M.copy(
                color = colors.white
            )
        )
    }
}

@Preview
@Composable
fun EmotiaButtonPreview() {
    EmotiaButton(text = "내 감정은 무기력이야")
}

@Preview
@Composable
fun EmotiaButtonDisabledPreview() {
    EmotiaButton(text = "Button", enabled = false)
}

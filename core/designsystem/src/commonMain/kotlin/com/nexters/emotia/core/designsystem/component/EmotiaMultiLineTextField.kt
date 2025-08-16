import androidx.compose.foundation.background
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsFocusedAsState
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.nexters.emotia.core.designsystem.theme.EmotiaTheme
import com.nexters.emotia.core.designsystem.theme.LocalEmotiaColors
import org.jetbrains.compose.ui.tooling.preview.Preview

internal enum class MultiLineTextFieldState {
    DISABLED,   // 비활성화 상태
    FOCUSED,    // 포커스 있고 비어있음 (입력 중)
    TYPING,     // 포커스 있고 텍스트 있음 (타이핑 중)
    TYPED       // 포커스 없고 텍스트 있음 (입력 완료)
}

internal fun getMultiLineTextFieldState(
    enabled: Boolean,
    isFocused: Boolean,
    hasText: Boolean,
): MultiLineTextFieldState {
    return when {
        !enabled -> MultiLineTextFieldState.DISABLED
        isFocused && hasText -> MultiLineTextFieldState.TYPING
        isFocused && !hasText -> MultiLineTextFieldState.FOCUSED
        !isFocused && hasText -> MultiLineTextFieldState.TYPED
        else -> MultiLineTextFieldState.FOCUSED
    }
}

@Composable
internal fun MultiLineTextFieldState.getBorderColor(): Color {
    val colors = LocalEmotiaColors.current
    return when (this) {
        MultiLineTextFieldState.DISABLED -> colors.lightGray
        MultiLineTextFieldState.FOCUSED -> colors.primaryLight
        MultiLineTextFieldState.TYPING -> colors.primaryLight
        MultiLineTextFieldState.TYPED -> colors.primaryLight
    }
}

@Composable
internal fun MultiLineTextFieldState.getTextColor(): Color {
    val colors = LocalEmotiaColors.current
    return colors.white
}

@Composable
internal fun MultiLineTextFieldState.getPlaceholderColor(): Color {
    val colors = LocalEmotiaColors.current
    return colors.lightGray
}

@Composable
fun EmotiaMultiLineTextField(
    value: String,
    onValueChange: (String) -> Unit,
    modifier: Modifier = Modifier,
    placeholder: String = "",
    enabled: Boolean = true,
) {
    val interactionSource = remember { MutableInteractionSource() }
    val colors = LocalEmotiaColors.current
    val isFocused by interactionSource.collectIsFocusedAsState()

    val state = remember(enabled, isFocused, value) {
        getMultiLineTextFieldState(
            enabled = enabled,
            isFocused = isFocused,
            hasText = value.isNotEmpty()
        )
    }

    OutlinedTextField(
        value = value,
        onValueChange = onValueChange,
        modifier = modifier
            .fillMaxWidth()
            .background(
                color = colors.darkGray,
                shape = RoundedCornerShape(12.dp)
            ),
        enabled = enabled,
        placeholder = {
            if (placeholder.isNotEmpty() && value.isEmpty()) {
                Text(
                    text = placeholder,
                    fontSize = 14.sp,
                    color = state.getPlaceholderColor()
                )
            }
        },
        singleLine = false,
        shape = RoundedCornerShape(12.dp),
        colors = OutlinedTextFieldDefaults.colors(
            focusedTextColor = state.getTextColor(),
            unfocusedTextColor = state.getTextColor(),
            disabledTextColor = state.getTextColor(),
            focusedBorderColor = state.getBorderColor(),
            unfocusedBorderColor = state.getBorderColor(),
            disabledBorderColor = state.getBorderColor(),
            focusedContainerColor = Color.Transparent,
            unfocusedContainerColor = Color.Transparent,
            disabledContainerColor = Color.Transparent,
            cursorColor = colors.primaryLight
        ),
        interactionSource = interactionSource
    )
}

@Preview
@Composable
private fun MultiLineTextFieldDisabledPreview() {
    EmotiaTheme {
        EmotiaMultiLineTextField(
            value = "",
            onValueChange = { },
            placeholder = "비활성화 상태",
            enabled = false
        )
    }
}

@Preview
@Composable
private fun MultiLineTextFieldFocusedPreview() {
    EmotiaTheme {
        EmotiaMultiLineTextField(
            value = "",
            onValueChange = { },
            placeholder = "포커스 상태 (비어있음)"
        )
    }
}

@Preview
@Composable
private fun MultiLineTextFieldTypingPreview() {
    EmotiaTheme {
        EmotiaMultiLineTextField(
            value = "입력 중...\n여러 줄 텍스트",
            onValueChange = { },
            placeholder = "타이핑 상태"
        )
    }
}

@Preview
@Composable
private fun MultiLineTextFieldTypedPreview() {
    EmotiaTheme {
        EmotiaMultiLineTextField(
            value = "입력 완료된 텍스트\n여러 줄로 작성된 내용",
            onValueChange = { },
            placeholder = "입력 완료 상태"
        )
    }
}

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsFocusedAsState
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import com.nexters.emotia.core.designsystem.extension.rippleClickable
import com.nexters.emotia.core.designsystem.theme.EmotiaTheme
import com.nexters.emotia.core.designsystem.theme.EmotiaTheme.typography
import com.nexters.emotia.core.designsystem.theme.LocalEmotiaColors
import emotia.core.designsystem.generated.resources.Res
import emotia.core.designsystem.generated.resources.ic_chatting_send_default
import emotia.core.designsystem.generated.resources.ic_chatting_send_disabled
import emotia.core.designsystem.generated.resources.ic_chatting_send_pressed
import org.jetbrains.compose.resources.DrawableResource
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.ui.tooling.preview.Preview

internal enum class ChatTextFieldState {
    DISABLED,   // 비활성화 상태
    FOCUSED,    // 포커스 있고 비어있음 (입력 중)
    TYPING,     // 포커스 있고 텍스트 있음 (타이핑 중)
    TYPED       // 포커스 없고 텍스트 있음 (입력 완료)
}

internal fun getTextFieldState(
    enabled: Boolean,
    isFocused: Boolean,
    hasText: Boolean,
): ChatTextFieldState {
    return when {
        !enabled -> ChatTextFieldState.DISABLED
        isFocused && hasText -> ChatTextFieldState.TYPING
        isFocused && !hasText -> ChatTextFieldState.FOCUSED
        !isFocused && hasText -> ChatTextFieldState.TYPED
        else -> ChatTextFieldState.FOCUSED
    }
}

@Composable
internal fun ChatTextFieldState.getBorderColor(): Color {
    val colors = LocalEmotiaColors.current
    return when (this) {
        ChatTextFieldState.DISABLED -> colors.lightGray
        ChatTextFieldState.FOCUSED -> colors.primaryLight
        ChatTextFieldState.TYPING -> colors.primaryLight
        ChatTextFieldState.TYPED -> colors.primaryLight
    }
}

@Composable
internal fun getTextColor(): Color {
    val colors = LocalEmotiaColors.current
    return colors.white
}

@Composable
internal fun getPlaceholderColor(): Color {
    val colors = LocalEmotiaColors.current
    return colors.lightGray
}

internal fun ChatTextFieldState.getSendIconResource(): DrawableResource {
    return when (this) {
        ChatTextFieldState.DISABLED -> Res.drawable.ic_chatting_send_disabled
        ChatTextFieldState.FOCUSED -> Res.drawable.ic_chatting_send_disabled
        ChatTextFieldState.TYPING -> Res.drawable.ic_chatting_send_pressed
        ChatTextFieldState.TYPED -> Res.drawable.ic_chatting_send_default
    }
}

internal fun ChatTextFieldState.isSendEnabled(): Boolean {
    return when (this) {
        ChatTextFieldState.TYPING, ChatTextFieldState.TYPED -> true
        ChatTextFieldState.DISABLED, ChatTextFieldState.FOCUSED -> false
    }
}

@Composable
fun EmotiaChatTextField(
    value: String,
    onValueChange: (String) -> Unit,
    onSendClick: () -> Unit,
    modifier: Modifier = Modifier,
    placeholder: String = "",
    enabled: Boolean = true,
) {
    val interactionSource = remember { MutableInteractionSource() }
    val colors = LocalEmotiaColors.current
    val isFocused by interactionSource.collectIsFocusedAsState()

    val state = remember(enabled, isFocused, value) {
        getTextFieldState(
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
                shape = RoundedCornerShape(200.dp)
            )
            .border(
                width = 2.dp,
                color = state.getBorderColor(),
                shape = RoundedCornerShape(200.dp)
            ),
        enabled = enabled,
        placeholder = {
            if (placeholder.isNotEmpty() && value.isEmpty()) {
                Text(
                    text = placeholder,
                    style = typography.emotia14R,
                    color = colors.lightGray
                )
            }
        },
        trailingIcon = {
            val clickable = enabled && state.isSendEnabled()
            Image(
                painter = painterResource(state.getSendIconResource()),
                contentDescription = "전송",
                modifier = Modifier
                    .size(36.dp)
                    .let { base ->
                        if (clickable)
                            base.rippleClickable {
                                if (value.isNotEmpty() && enabled) onSendClick()
                            }
                        else base
                    }
            )
        },
        singleLine = true,
        keyboardOptions = KeyboardOptions.Default.copy(imeAction = ImeAction.Send),
        keyboardActions = KeyboardActions(
            onSend = { if (enabled && value.isNotEmpty()) onSendClick() }
        ),
        shape = RoundedCornerShape(200.dp),
        colors = OutlinedTextFieldDefaults.colors(
            focusedTextColor = getTextColor(),
            unfocusedTextColor = getTextColor(),
            disabledTextColor = getTextColor(),
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
private fun ChatTextFieldDisabledPreview() {
    EmotiaTheme {
        EmotiaChatTextField(
            value = "",
            onValueChange = { },
            placeholder = "비활성화 상태",
            enabled = false,
            onSendClick = { }
        )
    }
}

@Preview
@Composable
private fun ChatTextFieldFocusedPreview() {
    EmotiaTheme {
        EmotiaChatTextField(
            value = "",
            onValueChange = { },
            placeholder = "포커스 상태 (비어있음)",
            onSendClick = { }
        )
    }
}

@Preview
@Composable
private fun ChatTextFieldTypingPreview() {
    EmotiaTheme {
        EmotiaChatTextField(
            value = "입력 중...",
            onValueChange = { },
            placeholder = "타이핑 상태",
            onSendClick = { }
        )
    }
}

@Preview
@Composable
private fun ChatTextFieldTypedPreview() {
    EmotiaTheme {
        EmotiaChatTextField(
            value = "입력 완료",
            onValueChange = { },
            placeholder = "입력 완료 상태",
            onSendClick = { }
        )
    }
}

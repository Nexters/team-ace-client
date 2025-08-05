package com.nexters.emotia.core.designsystem.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsFocusedAsState
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.IconButton
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
import com.nexters.emotia.core.designsystem.token.LocalEmotiaColors
import emotia.core.designsystem.generated.resources.Res
import emotia.core.designsystem.generated.resources.ic_chatting_send_default
import emotia.core.designsystem.generated.resources.ic_chatting_send_disabled
import emotia.core.designsystem.generated.resources.ic_chatting_send_pressed
import org.jetbrains.compose.resources.DrawableResource
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
fun getChattingSendIconResource(
    enabled: Boolean,
    isPressed: Boolean
): DrawableResource? {
    return when {
        !enabled -> Res.drawable.ic_chatting_send_disabled
        isPressed -> Res.drawable.ic_chatting_send_pressed
        else -> Res.drawable.ic_chatting_send_default
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
    interactionSource: MutableInteractionSource = remember { MutableInteractionSource() }
) {
    val colors = LocalEmotiaColors.current
    val isFocused by interactionSource.collectIsFocusedAsState()
    val hasText = value.isNotEmpty()

    // 입력 내용이 있을 때만 아이콘 활성화
    val isIconEnabled = hasText && enabled
    val iconResource = getChattingSendIconResource(
        enabled = isIconEnabled,
        isPressed = hasText && isFocused
    )

    OutlinedTextField(
        value = value,
        onValueChange = onValueChange,
        modifier = modifier.fillMaxWidth(),
        enabled = enabled,
        placeholder = {
            if (placeholder.isNotEmpty() && value.isEmpty()) {
                Text(
                    text = placeholder,
                    fontSize = 14.sp,
                    color = if (isFocused) colors.lightGray else colors.lightGray
                )
            }
        },
        trailingIcon = {
            IconButton(
                onClick = onSendClick,
                enabled = isIconEnabled
            ) {
                iconResource?.let { res ->
                    Image(
                        painter = painterResource(res),
                        contentDescription = "Send",
                        modifier = Modifier.size(36.dp)
                    )
                }
            }
        },
        singleLine = true,
        shape = RoundedCornerShape(200.dp),
        colors = OutlinedTextFieldDefaults.colors(
            focusedTextColor = colors.white,
            unfocusedTextColor = colors.white,
            disabledTextColor = colors.white,
            focusedBorderColor = colors.primaryLight,
            unfocusedBorderColor = if (isFocused || hasText) colors.primaryLight else colors.lightGray,
            disabledBorderColor = colors.lightGray,
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
fun ChatTextFieldPreview() {
    EmotiaTheme {
        EmotiaChatTextField(
            value = "",
            onValueChange = { },
            placeholder = "요정에게 지금 기분을 설명해 보자 ",
            onSendClick = { }
        )
    }
}
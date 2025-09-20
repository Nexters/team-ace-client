package com.nexters.emotia.core.designsystem.extension

import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.material3.ripple
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

/**
 * 연속 클릭을 방지하는 ripple Clickable extension
 */
@Composable
fun Modifier.rippleClickable(
    debounceTime: Long = 500L,
    onClick: () -> Unit,
): Modifier {
    val scope = rememberCoroutineScope()
    var isClickable by remember { mutableStateOf(true) }

    return this.clickable(
        interactionSource = remember { MutableInteractionSource() },
        indication = ripple()
    ) {
        if (isClickable) {
            isClickable = false
            onClick()
            scope.launch {
                delay(debounceTime)
                isClickable = true
            }
        }
    }
}
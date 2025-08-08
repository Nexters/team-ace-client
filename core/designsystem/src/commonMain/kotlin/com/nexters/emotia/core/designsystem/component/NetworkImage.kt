package com.nexters.emotia.core.designsystem.component

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import coil3.compose.LocalPlatformContext
import coil3.request.ImageRequest
import coil3.request.crossfade

@Composable
fun NetworkImage(
    imageUrl: String?,
    modifier: Modifier = Modifier,
    contentScale: ContentScale = ContentScale.Crop,
    contentDescription: String? = null,
    placeholder: @Composable (() -> Unit)? = null,
    error: @Composable (() -> Unit)? = null,
) {
    var isLoading by remember { mutableStateOf(true) }
    var isError by remember { mutableStateOf(false) }

    val context = LocalPlatformContext.current

    // Fast path: null/blank URL
    if (imageUrl.isNullOrBlank()) {
        Box(modifier = modifier, contentAlignment = Alignment.Center) {
            error?.invoke() ?: Text(
                text = "🧚‍♀️",
                style = MaterialTheme.typography.displayLarge
            )
        }
        return
    }

    // Build ImageRequest once per URL
    val request = remember(imageUrl, contentScale, contentDescription) {
        ImageRequest.Builder(context)
            .data(imageUrl)
            .crossfade(true)
            .build()
    }

    Box(modifier = modifier, contentAlignment = Alignment.Center) {
        // 로딩이나 에러 상태일 때 표시할 컴포넌트들
        when {
            isError -> {
                error?.invoke() ?: Text(
                    text = "🧚‍♀️",
                    style = MaterialTheme.typography.displayLarge
                )
            }

            isLoading -> {
                placeholder?.invoke() ?: CircularProgressIndicator(
                    modifier = Modifier.size(32.dp),
                    color = MaterialTheme.colorScheme.primary
                )
            }
        }

        // 실제 이미지
        AsyncImage(
            model = request,
            contentDescription = contentDescription,
            modifier = Modifier.fillMaxSize(),
            contentScale = contentScale,
            onLoading = {
                println("🔄 NetworkImage loading: $imageUrl")
                isLoading = true
                isError = false
            },
            onSuccess = {
                println("✅ NetworkImage loaded successfully: $imageUrl")
                isLoading = false
                isError = false
            },
            onError = { errorResult ->
                println("❌ NetworkImage loading failed: $imageUrl")
                println("❌ Error: ${errorResult.result.throwable}")
                isLoading = false
                isError = true
            }
        )
    }
}

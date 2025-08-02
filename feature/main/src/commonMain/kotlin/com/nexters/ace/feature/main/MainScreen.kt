package com.nexters.ace.feature.main

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import org.koin.compose.viewmodel.koinViewModel

@Composable
expect fun MainScreen(modifier: Modifier = Modifier)

@Composable
fun MainContent(modifier: Modifier = Modifier) {
    val viewModel: MainViewModel = koinViewModel()
    val ipInfoText by viewModel.ipInfoText.collectAsState()

    Box(
        modifier = modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Text(ipInfoText)
    }
}

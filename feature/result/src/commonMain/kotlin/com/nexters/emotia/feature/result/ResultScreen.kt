package com.nexters.emotia.feature.result

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun ResultScreen(
    onNavigateToOnBoarding: () -> Unit,
    onNavigateToChatting: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = "Result Screen",
            modifier = Modifier.padding(bottom = 32.dp)
        )

        Button(
            onClick = onNavigateToChatting,
            modifier = Modifier.padding(bottom = 12.dp)
        ) {
            Text("Go to Chatting")
        }

        OutlinedButton(onClick = onNavigateToOnBoarding) {
            Text("Go to OnBoarding")
        }
    }
}

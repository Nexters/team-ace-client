package com.nexters.emotia.feature.onboarding

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.nexters.emotia.getPlatform

@Composable
fun OnBoardingScreen(
    viewModel: OnBoardingViewModel,
    onNavigateToChatting: () -> Unit,
    modifier: Modifier = Modifier
) {
    val uiState by viewModel.uiState.collectAsState()
    
    LaunchedEffect(Unit) {
        val deviceUuid = getPlatform().generateDeviceUuid()
        println("[OnBoardingScreen] 화면 진입 - 자동 로그인 시작, UUID: $deviceUuid")
        viewModel.performAutoLogin(deviceUuid)
    }
    
    LaunchedEffect(uiState.isLoginSuccess) {
        if (uiState.isLoginSuccess) {
            println("[OnBoardingScreen] 로그인 성공, 채팅 화면으로 이동")
            onNavigateToChatting()
        }
    }
    
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        when {
            uiState.isLoading -> {
                CircularProgressIndicator()
                Text(
                    text = "로그인 중...",
                    modifier = Modifier.padding(top = 16.dp)
                )
            }
            uiState.errorMessage != null -> {
                Text(
                    text = "오류: ${uiState.errorMessage}",
                    modifier = Modifier.padding(bottom = 16.dp)
                )
                Button(onClick = {
                    val deviceUuid = getPlatform().generateDeviceUuid()
                    viewModel.performAutoLogin(deviceUuid)
                }) {
                    Text("다시 시도")
                }
            }
            uiState.isLoginSuccess -> {
                Text("로그인 성공!")
            }
            else -> {
                Text(
                    text = "감치왕국에 오신걸 환영합니다.",
                    modifier = Modifier.padding(bottom = 32.dp)
                )
                
                Button(onClick = onNavigateToChatting) {
                    Text("Go to Chatting1")
                }
            }
        }
    }
}

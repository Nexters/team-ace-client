package com.nexters.emotia.feature.onboarding

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nexters.emotia.core.domain.onboarding.LoginRepository
import com.nexters.emotia.core.domain.onboarding.RegisterRequest
import com.nexters.emotia.core.domain.onboarding.entity.LoginEntity
import com.nexters.emotia.core.domain.onboarding.usecase.TokenUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

data class OnBoardingUiState(
    val isLoading: Boolean = false,
    val loginEntity: LoginEntity? = null,
    val errorMessage: String? = null,
    val isLoginSuccess: Boolean = false
)

class OnBoardingViewModel(
    private val loginRepository: LoginRepository,
    private val tokenUseCase: TokenUseCase
) : ViewModel() {
    
    private val _uiState = MutableStateFlow(OnBoardingUiState())
    val uiState: StateFlow<OnBoardingUiState> = _uiState.asStateFlow()

    // 토큰 상태도 노출
    val isLoggedIn: StateFlow<Boolean> = tokenUseCase.isLoggedIn
    val currentToken: StateFlow<LoginEntity?> = tokenUseCase.currentToken
    
    fun performAutoLogin(deviceUuid: String) {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(isLoading = true, errorMessage = null)
            
            println("[OnBoardingViewModel] 자동 로그인 시작 - UUID: $deviceUuid")
            
            // 1단계: 로그인 시도
            val loginResult = loginRepository.login(deviceUuid)
            
            if (loginResult.isSuccess) {
                val loginEntity = loginResult.getOrNull()!!
                println("[OnBoardingViewModel] 로그인 성공")

                // 토큰 저장
                tokenUseCase.saveToken(loginEntity)
                
                _uiState.value = _uiState.value.copy(
                    isLoading = false,
                    loginEntity = loginEntity,
                    isLoginSuccess = true
                )
            } else {
                println("[OnBoardingViewModel] 로그인 실패, 회원가입 시도")
                
                // 2단계: 회원가입 시도
                val registerRequest = RegisterRequest(
                    username = deviceUuid,
                    nickname = deviceUuid
                )
                val registerResult = loginRepository.register(registerRequest)
                
                if (registerResult.isSuccess) {
                    val loginEntity = registerResult.getOrNull()!!
                    println("[OnBoardingViewModel] 회원가입 성공")

                    // 토큰 저장
                    tokenUseCase.saveToken(loginEntity)
                    
                    _uiState.value = _uiState.value.copy(
                        isLoading = false,
                        loginEntity = loginEntity,
                        isLoginSuccess = true
                    )
                } else {
                    val errorMessage = registerResult.exceptionOrNull()?.message ?: "회원가입 실패"
                    println("[OnBoardingViewModel] 회원가입 실패: $errorMessage")
                    _uiState.value = _uiState.value.copy(
                        isLoading = false,
                        errorMessage = errorMessage
                    )
                }
            }
        }
    }

    // 토큰 관련 유틸리티 함수들
    suspend fun getAccessToken(): String? = tokenUseCase.getAccessToken()

    suspend fun getRefreshToken(): String? = tokenUseCase.getRefreshToken()

    suspend fun getUsername(): String? = tokenUseCase.getUsername()

    suspend fun updateAccessToken(newAccessToken: String) {
        tokenUseCase.updateAccessToken(newAccessToken)
    }

    suspend fun clearTokens() {
        tokenUseCase.clearTokens()
    }

    suspend fun hasValidToken(): Boolean = tokenUseCase.hasValidToken()
}
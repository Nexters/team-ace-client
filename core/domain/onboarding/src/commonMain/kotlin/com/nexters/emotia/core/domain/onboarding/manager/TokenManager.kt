package com.nexters.emotia.core.domain.onboarding.manager

import com.nexters.emotia.core.domain.onboarding.entity.LoginEntity
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class TokenManager {
    private val _currentToken = MutableStateFlow<LoginEntity?>(null)
    val currentToken: StateFlow<LoginEntity?> = _currentToken.asStateFlow()

    private val _isLoggedIn = MutableStateFlow(false)
    val isLoggedIn: StateFlow<Boolean> = _isLoggedIn.asStateFlow()

    fun saveToken(loginEntity: LoginEntity) {
        _currentToken.value = loginEntity
        _isLoggedIn.value = true
        println("[TokenManager] 토큰 저장 완료: ${loginEntity.username}")
    }

    fun getAccessToken(): String? {
        return _currentToken.value?.accessToken
    }

    fun getRefreshToken(): String? {
        return _currentToken.value?.refreshToken
    }

    fun getUsername(): String? {
        return _currentToken.value?.username
    }

    fun updateAccessToken(newAccessToken: String) {
        val currentLogin = _currentToken.value
        if (currentLogin != null) {
            _currentToken.value = currentLogin.copy(accessToken = newAccessToken)
            println("[TokenManager] 액세스 토큰 업데이트 완료")
        }
    }

    fun clearTokens() {
        _currentToken.value = null
        _isLoggedIn.value = false
        println("[TokenManager] 토큰 클리어 완료")
    }

    fun hasValidToken(): Boolean {
        return _currentToken.value != null &&
                !_currentToken.value!!.accessToken.isBlank() &&
                !_currentToken.value!!.refreshToken.isBlank()
    }
}
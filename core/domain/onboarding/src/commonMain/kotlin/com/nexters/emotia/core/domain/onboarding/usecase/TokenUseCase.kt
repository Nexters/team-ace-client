package com.nexters.emotia.core.domain.onboarding.usecase

import com.nexters.emotia.core.domain.onboarding.entity.LoginEntity
import com.nexters.emotia.core.domain.onboarding.repository.TokenRepository
import kotlinx.coroutines.flow.StateFlow

class TokenUseCase(
    private val tokenRepository: TokenRepository
) {
    val currentToken: StateFlow<LoginEntity?> = tokenRepository.currentToken
    val isLoggedIn: StateFlow<Boolean> = tokenRepository.isLoggedIn

    suspend fun saveToken(loginEntity: LoginEntity) {
        tokenRepository.saveToken(loginEntity)
    }

    suspend fun getAccessToken(): String? {
        return tokenRepository.getAccessToken()
    }

    suspend fun getRefreshToken(): String? {
        return tokenRepository.getRefreshToken()
    }

    suspend fun getUsername(): String? {
        return tokenRepository.getUsername()
    }

    suspend fun updateAccessToken(newAccessToken: String) {
        tokenRepository.updateAccessToken(newAccessToken)
    }

    suspend fun clearTokens() {
        tokenRepository.clearTokens()
    }

    suspend fun hasValidToken(): Boolean {
        return tokenRepository.hasValidToken()
    }
}
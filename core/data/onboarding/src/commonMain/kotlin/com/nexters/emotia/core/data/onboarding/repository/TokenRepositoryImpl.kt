package com.nexters.emotia.core.data.onboarding.repository

import com.nexters.emotia.core.domain.onboarding.entity.LoginEntity
import com.nexters.emotia.core.domain.onboarding.manager.TokenManager
import com.nexters.emotia.core.domain.onboarding.repository.TokenRepository
import kotlinx.coroutines.flow.StateFlow

class TokenRepositoryImpl(
    private val tokenManager: TokenManager
) : TokenRepository {

    override val currentToken: StateFlow<LoginEntity?> = tokenManager.currentToken
    override val isLoggedIn: StateFlow<Boolean> = tokenManager.isLoggedIn

    override suspend fun saveToken(loginEntity: LoginEntity) {
        tokenManager.saveToken(loginEntity)
    }

    override suspend fun getAccessToken(): String? {
        return tokenManager.getAccessToken()
    }

    override suspend fun getRefreshToken(): String? {
        return tokenManager.getRefreshToken()
    }

    override suspend fun getUsername(): String? {
        return tokenManager.getUsername()
    }

    override suspend fun updateAccessToken(newAccessToken: String) {
        tokenManager.updateAccessToken(newAccessToken)
    }

    override suspend fun clearTokens() {
        tokenManager.clearTokens()
    }

    override suspend fun hasValidToken(): Boolean {
        return tokenManager.hasValidToken()
    }
}
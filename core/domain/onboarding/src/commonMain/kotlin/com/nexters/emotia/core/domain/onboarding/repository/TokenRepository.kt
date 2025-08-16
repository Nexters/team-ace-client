package com.nexters.emotia.core.domain.onboarding.repository

import com.nexters.emotia.core.domain.onboarding.entity.LoginEntity
import kotlinx.coroutines.flow.StateFlow

interface TokenRepository {
    val currentToken: StateFlow<LoginEntity?>
    val isLoggedIn: StateFlow<Boolean>

    suspend fun saveToken(loginEntity: LoginEntity)
    suspend fun getAccessToken(): String?
    suspend fun getRefreshToken(): String?
    suspend fun getUsername(): String?
    suspend fun updateAccessToken(newAccessToken: String)
    suspend fun clearTokens()
    suspend fun hasValidToken(): Boolean
}
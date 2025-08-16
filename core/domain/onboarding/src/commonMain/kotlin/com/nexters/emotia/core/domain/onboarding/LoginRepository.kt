package com.nexters.emotia.core.domain.onboarding

import com.nexters.emotia.core.domain.onboarding.entity.LoginEntity

interface LoginRepository {
    suspend fun login(userName: String): Result<LoginEntity>
    suspend fun register(request: RegisterRequest): Result<LoginEntity>
    suspend fun refreshToken(refreshToken: String): Result<String>
}
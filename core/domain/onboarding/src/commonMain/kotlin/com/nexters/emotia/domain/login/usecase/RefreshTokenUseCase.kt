package com.nexters.emotia.domain.login.usecase

import com.nexters.emotia.core.domain.onboarding.LoginRepository

class RefreshTokenUseCase(
    private val loginRepository: LoginRepository
) {
    suspend fun execute(refreshToken: String): Result<String> {
        return loginRepository.refreshToken(refreshToken)
    }
}
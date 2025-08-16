package com.nexters.emotia.core.domain.onboarding.usecase

import com.nexters.emotia.core.domain.onboarding.LoginRepository
import com.nexters.emotia.core.domain.onboarding.RegisterRequest
import com.nexters.emotia.core.domain.onboarding.entity.LoginEntity

class AutoLoginUseCase(
    private val loginRepository: LoginRepository
) {
    suspend fun execute(deviceUuid: String): Result<LoginEntity> {
        return try {
            val loginResult = loginRepository.login(deviceUuid)
            
            if (loginResult.isSuccess) {
                loginResult
            } else {
                val registerRequest = RegisterRequest(
                    username = deviceUuid,
                    nickname = deviceUuid
                )
                loginRepository.register(registerRequest)
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}
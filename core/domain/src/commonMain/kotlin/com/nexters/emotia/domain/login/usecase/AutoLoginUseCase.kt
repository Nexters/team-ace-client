package com.nexters.emotia.domain.login.usecase

import com.nexters.emotia.domain.login.Entity.LoginEntity
import com.nexters.emotia.domain.login.Entity.RegisterRequest
import com.nexters.emotia.domain.login.LoginRepository

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
package com.nexters.emotia.network

import com.nexters.emotia.network.OnBoardingDTO.LoginRequest
import com.nexters.emotia.network.OnBoardingDTO.LoginResponse
import com.nexters.emotia.network.OnBoardingDTO.RefreshTokenInfoRequest
import com.nexters.emotia.network.OnBoardingDTO.RegisterRequest
import com.nexters.emotia.network.OnBoardingDTO.RegisterResponse
import com.nexters.emotia.network.OnBoardingDTO.TokenInfoResponse

class AuthApiService(
    private val network: EmotiaNetwork,
) {
    suspend fun login(request: LoginRequest): LoginResponse {
        return network.post(LOGIN_ENDPOINT, request)
    }

    suspend fun register(request: RegisterRequest): RegisterResponse {
        return network.post(REGISTER_ENDPOINT, request)
    }

    suspend fun refresh(request: RefreshTokenInfoRequest): TokenInfoResponse {
        return network.post(REFRESH_TOKEN_ENDPOINT, request)
    }

    companion object {
        const val LOGIN_ENDPOINT = "api/v1/auth/login"
        const val REGISTER_ENDPOINT = "api/v1/auth/signup"
        const val REFRESH_TOKEN_ENDPOINT = "api/v1/auth/refresh"
    }
}
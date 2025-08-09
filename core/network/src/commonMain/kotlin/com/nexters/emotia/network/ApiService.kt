package com.nexters.emotia.network

import com.nexters.emotia.network.OnBoardingDTO.LoginRequest
import com.nexters.emotia.network.OnBoardingDTO.LoginResponse
import com.nexters.emotia.network.OnBoardingDTO.RegisterRequest
import com.nexters.emotia.network.OnBoardingDTO.RefreshTokenInfoRequest
import com.nexters.emotia.network.OnBoardingDTO.RegisterResponse
import com.nexters.emotia.network.OnBoardingDTO.TokenInfoResponse

class ApiService(
    private val network: EmotiaNetwork,
) {
    suspend fun getGeolocationInfo(ipAddress: String): FakeResponse {
        return network.get("json/$ipAddress")
    }

    suspend fun login(repuest: LoginRequest): LoginResponse {
        return network.post(LOGIN_ENDPOINT, repuest)
    }

    suspend fun register(repuest: RegisterRequest): RegisterResponse {
        return network.post(REGISTER_ENDPOINT, repuest)
    }

    suspend fun refrash(repuest: RefreshTokenInfoRequest): TokenInfoResponse {
        return network.post(REFRESH_TOKEN_ENDPOINT, repuest)
    }

    companion object {
        const val LOGIN_ENDPOINT = "api/v1/auth/login"
        const val REGISTER_ENDPOINT = "api/v1/auth/signup"
        const val REFRESH_TOKEN_ENDPOINT = "api/v1/auth/refresh"
    }
}
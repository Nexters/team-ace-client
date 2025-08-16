package com.nexters.emotia.core.data.onboarding.datasource

import com.nexters.emotia.network.AuthApiService
import com.nexters.emotia.network.OnBoardingDTO.LoginRequest
import com.nexters.emotia.network.OnBoardingDTO.LoginResponse
import com.nexters.emotia.network.OnBoardingDTO.RefreshTokenInfoRequest
import com.nexters.emotia.network.OnBoardingDTO.RegisterRequest
import com.nexters.emotia.network.OnBoardingDTO.RegisterResponse
import com.nexters.emotia.network.OnBoardingDTO.TokenInfoResponse

class LoginDataSourceImpl(
    private val apiService: AuthApiService
) : LoginDataSource {

    override suspend fun login(request: LoginRequest): LoginResponse {
        return apiService.login(request = request)
    }

    override suspend fun register(request: RegisterRequest): RegisterResponse {
        return apiService.register(request = request)
    }

    override suspend fun refreshToken(request: RefreshTokenInfoRequest): TokenInfoResponse {
        return apiService.refreshToken(request = request)
    }
}
package com.nexters.emotia.core.data.onboarding.datasource

import com.nexters.emotia.network.OnBoardingDTO.LoginRequest
import com.nexters.emotia.network.OnBoardingDTO.LoginResponse
import com.nexters.emotia.network.OnBoardingDTO.RefreshTokenInfoRequest
import com.nexters.emotia.network.OnBoardingDTO.RegisterRequest
import com.nexters.emotia.network.OnBoardingDTO.RegisterResponse
import com.nexters.emotia.network.OnBoardingDTO.TokenInfoResponse

interface LoginDataSource {
    suspend fun login(request: LoginRequest): LoginResponse
    suspend fun register(request: RegisterRequest): RegisterResponse
    suspend fun refreshToken(request: RefreshTokenInfoRequest): TokenInfoResponse
}
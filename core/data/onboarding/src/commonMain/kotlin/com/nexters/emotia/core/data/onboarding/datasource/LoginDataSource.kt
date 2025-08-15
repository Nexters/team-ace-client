package com.nexters.emotia.core.data.onboarding.datasource

import com.nexters.emotia.network.OnBoardingDTO.LoginRequest
import com.nexters.emotia.network.OnBoardingDTO.LoginResponse
import com.nexters.emotia.network.OnBoardingDTO.RegisterRequest
import com.nexters.emotia.network.OnBoardingDTO.RegisterResponse

interface LoginDataSource {
    suspend fun login(request: LoginRequest): LoginResponse
    suspend fun register(request: RegisterRequest): RegisterResponse
}
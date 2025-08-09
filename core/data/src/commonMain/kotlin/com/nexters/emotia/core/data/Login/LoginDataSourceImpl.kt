package com.nexters.emotia.core.data.Login

import com.nexters.emotia.network.ApiService
import com.nexters.emotia.network.OnBoardingDTO.LoginRequest
import com.nexters.emotia.network.OnBoardingDTO.LoginResponse
import com.nexters.emotia.network.OnBoardingDTO.RegisterRequest
import com.nexters.emotia.network.OnBoardingDTO.RegisterResponse

class LoginDataSourceImpl(
    private val apiService: ApiService
) : LoginDataSource {

    override suspend fun login(request: LoginRequest): LoginResponse {
        return apiService.login(repuest = request)
    }

    override suspend fun register(request: RegisterRequest): RegisterResponse {
        return apiService.register(repuest = request)
    }
}
package com.nexters.emotia.network.OnBoardingDTO

import kotlinx.serialization.Serializable

@Serializable
data class RegisterRequest(
    val username: String,
    val nickname: String
)

@Serializable
data class RegisterResponse(
    val success: Boolean,
    val data: RegisterData,
    val error: String? = null
)

@Serializable
data class RegisterData(
    val username: String,
    val accessToken: String,
    val refreshToken: String
)
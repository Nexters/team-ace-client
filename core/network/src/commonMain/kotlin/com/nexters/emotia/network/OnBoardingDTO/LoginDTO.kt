package com.nexters.emotia.network.OnBoardingDTO

import kotlinx.serialization.Serializable

@Serializable
data class LoginRequest(
    val username: String
)

@Serializable
data class LoginResponse(
    val success: Boolean,
    val data: LoginData,
    val error: String? = null
)

@Serializable
data class LoginData(
    val username: String,
    val accessToken: String,
    val refreshToken: String
)
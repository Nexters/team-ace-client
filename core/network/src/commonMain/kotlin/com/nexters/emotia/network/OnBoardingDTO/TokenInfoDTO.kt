package com.nexters.emotia.network.OnBoardingDTO

import kotlinx.serialization.Serializable

@Serializable
data class RefreshTokenInfoRequest (
    val refreshToken: String
)

@Serializable
data class TokenInfoResponse (
    val accessToken: String
)
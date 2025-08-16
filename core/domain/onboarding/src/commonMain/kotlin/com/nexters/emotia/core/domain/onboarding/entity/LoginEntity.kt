package com.nexters.emotia.core.domain.onboarding.entity

data class LoginEntity(
    val username: String,
    val accessToken: String,
    val refreshToken: String
)
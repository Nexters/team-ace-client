package com.nexters.emotia.domain.login.Entity

data class LoginEntity(
    val username: String,
    val accessToken: String,
    val refreshToken: String
)
package com.nexters.emotia.domain.login

import com.nexters.emotia.domain.login.Entity.LoginEntity
import com.nexters.emotia.domain.login.Entity.RegisterRequest

interface LoginRepository {
    suspend fun login(userName: String): Result<LoginEntity>
    suspend fun register(request: RegisterRequest): Result<LoginEntity>
}
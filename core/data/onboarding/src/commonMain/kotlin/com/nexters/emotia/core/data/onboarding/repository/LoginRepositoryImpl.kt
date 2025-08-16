package com.nexters.emotia.core.data.onboarding.repository

import com.nexters.emotia.core.data.onboarding.datasource.LoginDataSource
import com.nexters.emotia.core.domain.onboarding.LoginRepository
import com.nexters.emotia.core.domain.onboarding.entity.LoginEntity
import com.nexters.emotia.core.domain.onboarding.RegisterRequest as DomainRegisterRequest
import com.nexters.emotia.network.OnBoardingDTO.LoginRequest as NetworkLoginRequest
import com.nexters.emotia.network.OnBoardingDTO.RegisterRequest as NetworkRegisterRequest
import com.nexters.emotia.network.OnBoardingDTO.RefreshTokenInfoRequest

class LoginRepositoryImpl(
    private val loginDataSource: LoginDataSource
) : LoginRepository {

    override suspend fun login(userName: String): Result<LoginEntity> {
        return try {
            println("[LoginRepository] 로그인 시작 - username: $userName")
            val networkRequest = NetworkLoginRequest(username = userName)
            val response = loginDataSource.login(networkRequest)
            val entity = LoginEntity(
                username = response.data?.username ?: "",
                accessToken = response.data?.accessToken ?: "",
                refreshToken = response.data?.refreshToken ?: ""
            )
            println("[LoginRepository] 로그인 성공 - username: ${entity.username}, accessToken: ${entity.accessToken.take(10)}...")
            Result.success(entity)
        } catch (e: Exception) {
            println("[LoginRepository] 로그인 실패 - username: $userName, error: ${e.message}")
            Result.failure(e)
        }
    }

    override suspend fun register(request: DomainRegisterRequest): Result<LoginEntity> {
        return try {
            println("[LoginRepository] 회원가입 시작 - username: ${request.username}, nickname: ${request.nickname}")
            val networkRequest = NetworkRegisterRequest(
                username = request.username,
                nickname = request.nickname
            )
            val response = loginDataSource.register(networkRequest)
            val entity = LoginEntity(
                username = response.data.username,
                accessToken = response.data.accessToken,
                refreshToken = response.data.refreshToken
            )
            println("[LoginRepository] 회원가입 성공 - username: ${entity.username}, accessToken: ${entity.accessToken.take(10)}...")
            Result.success(entity)
        } catch (e: Exception) {
            println("[LoginRepository] 회원가입 실패 - username: ${request.username}, error: ${e.message}")
            Result.failure(e)
        }
    }

    override suspend fun refreshToken(refreshToken: String): Result<String> {
        return try {
            println("[LoginRepository] 토큰 갱신 시작 - refreshToken: ${refreshToken.take(10)}...")
            val request = RefreshTokenInfoRequest(refreshToken = refreshToken)
            val response = loginDataSource.refreshToken(request)
            println("[LoginRepository] 토큰 갱신 성공 - accessToken: ${response.accessToken.take(10)}...")
            Result.success(response.accessToken)
        } catch (e: Exception) {
            println("[LoginRepository] 토큰 갱신 실패 - error: ${e.message}")
            Result.failure(e)
        }
    }
}
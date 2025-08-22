package com.nexters.emotia.network

import com.nexters.emotia.core.domain.onboarding.usecase.TokenUseCase
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.plugins.HttpTimeout
import io.ktor.client.plugins.auth.Auth
import io.ktor.client.plugins.auth.providers.BearerTokens
import io.ktor.client.plugins.auth.providers.bearer
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.defaultRequest
import io.ktor.client.plugins.logging.DEFAULT
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logger
import io.ktor.client.plugins.logging.Logging
import io.ktor.client.request.get
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.http.ContentType
import io.ktor.http.URLProtocol
import io.ktor.http.contentType
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json


class EmotiaNetwork(
    private val tokenUseCase: TokenUseCase
) {
    val httpClient = createHttpClient(NetworkConfig.baseUrl)

    private fun createHttpClient(hostName: String): HttpClient = HttpClient {
        install(ContentNegotiation) {
            val json = Json {
                ignoreUnknownKeys = true
                encodeDefaults = true
                prettyPrint = true
            }
            json(json)
        }
        install(Logging) {
            logger = Logger.DEFAULT
            level = LogLevel.ALL
        }
        install(HttpTimeout) {
            connectTimeoutMillis = TIMEOUT_MILLIS
            requestTimeoutMillis = TIMEOUT_MILLIS
            socketTimeoutMillis = TIMEOUT_MILLIS
        }
        install(Auth) {
            bearer {
                loadTokens {
                    val accessToken = tokenUseCase.getAccessToken()
                    val refreshToken = tokenUseCase.getRefreshToken()
                    if (!accessToken.isNullOrBlank()) {
                        BearerTokens(accessToken, refreshToken)
                    } else {
                        null
                    }
                }
                refreshTokens {
                    val refreshToken = tokenUseCase.getRefreshToken()
                    if (!refreshToken.isNullOrBlank()) {
                        // TODO: 리프레시 토큰으로 새 액세스 토큰 받아오는 로직
                        // 현재는 기존 토큰 반환
                        val accessToken = tokenUseCase.getAccessToken()
                        if (!accessToken.isNullOrBlank()) {
                            BearerTokens(accessToken, refreshToken)
                        } else {
                            null
                        }
                    } else {
                        null
                    }
                }
            }
        }
        defaultRequest {
            contentType(ContentType.Application.Json)
            url {
                protocol = URLProtocol.HTTPS
                host = hostName
            }
        }
    }

    suspend inline fun <reified T : Any> get(
        path: String,
        token: String? = null,
    ): T = httpClient.get(path) {
    }.body()

    /*
       * TODO : AUTH 작업 시 토큰 헤더를 위한 interceptor 필요
     */
    suspend inline fun <reified T : Any, reified R : Any> post(
        path: String,
        body: R,
        ): T = httpClient.post(path) {
        setBody(body)
    }.body()



    companion object Companion {
        private const val TIMEOUT_MILLIS = 10_000L
        const val TEST_TOKEN =
            ""
    }
}

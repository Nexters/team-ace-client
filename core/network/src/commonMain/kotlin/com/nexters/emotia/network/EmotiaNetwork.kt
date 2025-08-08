package com.nexters.emotia.network

import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.plugins.HttpTimeout
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.defaultRequest
import io.ktor.client.plugins.logging.DEFAULT
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logger
import io.ktor.client.plugins.logging.Logging
import io.ktor.client.request.get
import io.ktor.client.request.header
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.http.ContentType
import io.ktor.http.HttpHeaders
import io.ktor.http.URLProtocol
import io.ktor.http.contentType
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json

class EmotiaNetwork {
    val httpClient = createHttpClient(BASE_URL)

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
        defaultRequest {
            contentType(ContentType.Application.Json)
            url {
                protocol = URLProtocol.HTTP
                host = hostName
            }
        }
    }

    suspend inline fun <reified T : Any> get(path: String): T = httpClient.get(path).body()

    suspend inline fun <reified T : Any> get(
        path: String,
        token: String? = null
    ): T = httpClient.get(path) {
        token?.let {
            header(HttpHeaders.Authorization, "Bearer $it")
        }
    }.body()

    /*
       * TODO : AUTH 작업 시 토큰 헤더를 위한 interceptor 필요
     */
    suspend inline fun <reified T : Any, reified R : Any> post(
        path: String,
        body: R,
        token: String? = null,
    ): T = httpClient.post(path) {
        setBody(body)
        token?.let {
            header(HttpHeaders.Authorization, "Bearer $it")
        }
    }.body()

    companion object Companion {
        private const val TIMEOUT_MILLIS = 10_000L
        private const val BASE_URL = "223.130.157.12:8080"
        const val TEST_TOKEN =
            "eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiIxMjM0NTY3OCIsInVzZXJJZCI6NCwiaWF0IjoxNzU0NjY0MzAwLCJleHAiOjE3NTUyNjkxMDB9.7rwDwEMzTEh8Dj6E-jwtuAhd7ITArhfK02m8xyITdPv1e2Kp7Ph4AZLOW0aTmHpsmngHDsMr9yTZcNPQoPZzMQ"
    }
}

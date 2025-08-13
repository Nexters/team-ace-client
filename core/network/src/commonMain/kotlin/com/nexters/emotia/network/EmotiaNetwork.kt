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
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.http.ContentType
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
                // 우리 서버 나오고 수정 필요
                protocol = URLProtocol.HTTP
                host = hostName
            }
        }
    }

    suspend inline fun <reified T : Any> get(path: String): T = httpClient.get(path).body()

    suspend inline fun <reified T : Any> post(path: String, body: Any): T = 
        httpClient.post(path) { setBody(body) }.body()


    companion object Companion {
        private const val TIMEOUT_MILLIS = 10_000L
        // 우리 서버 나오고 수정 필요
        private const val BASE_URL = ""
    }
}

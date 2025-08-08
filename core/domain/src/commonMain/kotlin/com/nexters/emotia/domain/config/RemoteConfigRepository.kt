package com.nexters.emotia.domain.config

interface RemoteConfigRepository {
    suspend fun getChattingMaxCount(): Int
}
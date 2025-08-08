package com.nexters.emotia.core.data.config.repository

import com.nexters.emotia.domain.config.RemoteConfigRepository
import dev.gitlive.firebase.Firebase
import dev.gitlive.firebase.remoteconfig.remoteConfig
import kotlin.time.Duration.Companion.seconds

class RemoteConfigRepositoryImpl : RemoteConfigRepository {

    private val rc = Firebase.remoteConfig

    override suspend fun getChattingMaxCount(): Int {
        return try {
            rc.setDefaults("chatting_max_count" to 5L)
            
            // 개발 모드에서는 캐시 없이 즉시 fetch
            rc.settings {
                minimumFetchInterval = 0.seconds // 캐시 시간을 0으로 설정 (개발용)
            }
            
            rc.fetchAndActivate()
            rc.getValue("chatting_max_count").asLong().toInt()
        } catch (e: Exception) {
            println("Failed to get chatting_max_count: ${e.message}")
            5
        }
    }
}

package com.nexters.emotia.core.data.chat.mapper

import com.nexters.emotia.domain.chat.entity.ChattingRoom
import com.nexters.emotia.network.FakeResponse

fun FakeResponse.toDomain(): ChattingRoom {
    // DTO에서 도메인 모델로 변환
    return ChattingRoom(
        ipAddress = this.query,
        status = this.status,
        country = this.country ?: "",
        city = this.city ?: "",
        latitude = this.lat ?: 0.0,
        longitude = this.lon ?: 0.0
    )
}

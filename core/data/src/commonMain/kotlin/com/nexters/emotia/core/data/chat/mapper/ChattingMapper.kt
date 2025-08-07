package com.nexters.emotia.core.data.chat.mapper

import com.nexters.emotia.domain.chat.entity.ChattingRoom
import com.nexters.emotia.network.dto.response.CreateRoomResponse

fun CreateRoomResponse.toDomain(): ChattingRoom {
    val roomData = this.data ?: throw IllegalStateException("응답 데이터가 없습니다")

    return ChattingRoom(
        roomId = roomData.chatRoomId,
        firstMessage = roomData.chat
    )
}

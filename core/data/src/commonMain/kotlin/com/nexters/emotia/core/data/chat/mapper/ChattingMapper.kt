package com.nexters.emotia.core.data.chat.mapper

import com.nexters.emotia.domain.chat.entity.ChatMessage
import com.nexters.emotia.domain.chat.entity.ChattingRoom
import com.nexters.emotia.network.dto.response.CreateRoomResponse
import com.nexters.emotia.network.dto.response.SendChatResponse

fun CreateRoomResponse.toDomain(): ChattingRoom {
    val roomData = this.data ?: throw IllegalStateException("응답 데이터가 없습니다")

    return ChattingRoom(
        roomId = roomData.chatRoomId,
        firstMessage = roomData.chat
    )
}

fun SendChatResponse.toDomain(): ChatMessage {
    val chatData = this.data ?: throw IllegalStateException("응답 데이터가 없습니다")

    return ChatMessage(
        message = chatData.message
    )
}

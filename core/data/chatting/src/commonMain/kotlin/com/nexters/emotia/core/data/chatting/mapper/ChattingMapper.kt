package com.nexters.emotia.core.data.chatting.mapper

import com.nexters.emotia.core.domain.chatting.entity.ChatMessage
import com.nexters.emotia.core.domain.chatting.entity.ChattingRoom
import com.nexters.emotia.core.domain.chatting.entity.Fairy
import com.nexters.emotia.network.dto.response.CreateRoomResponse
import com.nexters.emotia.network.dto.response.FairyDto
import com.nexters.emotia.network.dto.response.GetFairiesResponse
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

fun GetFairiesResponse.toDomain(): List<Fairy> {
    val fairiesData = this.data ?: throw IllegalStateException("응답 데이터가 없습니다")

    return fairiesData.fairies.map { it.toDomain() }
}

fun FairyDto.toDomain(): Fairy {
    return Fairy(
        id = this.id,
        name = this.name,
        image = this.image,
        silhouetteImage = this.silhouetteImage,
        emotion = this.emotion,
        description = this.emotionDescription
    )
}

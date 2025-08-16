package com.nexters.emotia.core.database.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "chat_messages")

data class ChatMessageEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val text: String,
    val senderType: String, // "MINE", "OTHER"
    val timestamp: Long,
    val roomId: String? = null // 추후 채팅방 별 조회를 위해 미리 추가
)
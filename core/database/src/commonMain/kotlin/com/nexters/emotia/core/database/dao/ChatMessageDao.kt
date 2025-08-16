package com.nexters.emotia.core.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.nexters.emotia.core.database.entity.ChatMessageEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface ChatMessageDao {
    @Insert
    suspend fun insertMessage(message: ChatMessageEntity)

    @Insert
    suspend fun insertMessages(messages: List<ChatMessageEntity>)

    @Query("SELECT * FROM chat_messages WHERE roomId = :roomId OR roomId IS NULL ORDER BY timestamp ASC")
    fun getMessagesByRoomId(roomId: String?): Flow<List<ChatMessageEntity>>

    @Query("DELETE FROM chat_messages WHERE roomId = :roomId OR roomId IS NULL")
    suspend fun deleteMessagesByRoomId(roomId: String?)

    @Query("SELECT COUNT(*) FROM chat_messages WHERE roomId = :roomId OR roomId IS NULL")
    suspend fun getMessageCountByRoomId(roomId: String?): Int
}
package com.nexters.emotia.feature.chatting.contract

import com.nexters.emotia.core.domain.chatting.entity.Fairy
import com.nexters.emotia.feature.chatting.model.ChatMessage
import com.nexters.emotia.feature.chatting.model.EmotionOption
import kotlinx.collections.immutable.PersistentList
import kotlinx.collections.immutable.persistentListOf

data class ChattingState(
    val messages: PersistentList<ChatMessage> = persistentListOf(),
    val currentInputText: String = "",
    val emotionOptions: PersistentList<EmotionOption> = persistentListOf(),
    val isTextFieldEnabled: Boolean = true,
    val showEmotionChips: Boolean = false,
    val isLoading: Boolean = false,
    val roomId: String? = null,
    val error: String? = null,
    val fairies: PersistentList<Fairy> = persistentListOf(),
    val showFairyPager: Boolean = false,
    val selectedFairyIndex: Int = 1,
    val isRoomCreated: Boolean = false,
    val firstMessage: String = "",
)

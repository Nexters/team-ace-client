package com.nexters.emotia.feature.chatting.contract

import com.nexters.emotia.feature.chatting.model.EmotionOption


sealed interface ChattingIntent {
    data object CreateChatRoom : ChattingIntent
    data class InputTextChanged(val text: String) : ChattingIntent
    data object SendMessage : ChattingIntent
    data class SelectEmotionOption(val option: EmotionOption) : ChattingIntent
    data object ClearError : ChattingIntent
    data object LoadFairies : ChattingIntent
    data class SelectFairy(val index: Int) : ChattingIntent
}

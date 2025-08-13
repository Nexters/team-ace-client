package com.nexters.emotia.feature.chatting.contract

sealed interface ChattingSideEffect {
    data class ShowError(val message: String) : ChattingSideEffect
}
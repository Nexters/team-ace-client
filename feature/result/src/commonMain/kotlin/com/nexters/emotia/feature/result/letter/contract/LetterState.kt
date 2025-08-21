package com.nexters.emotia.feature.result.letter.contract

data class LetterState(
    val fairyId: Int = 0,
    val fairyName: String = "",
    val fairyImage: String = "",
    val chatRoomId: Int = 0,
    val contents: String = "",
    val isLoading: Boolean = false,
    val error: String? = null,
)
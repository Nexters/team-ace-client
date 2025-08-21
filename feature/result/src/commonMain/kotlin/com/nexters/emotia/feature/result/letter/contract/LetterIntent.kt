package com.nexters.emotia.feature.result.letter.contract

sealed interface LetterIntent {
    data class InitializeFairy(
        val fairyId: Int,
        val fairyName: String,
        val fairyImage: String,
        val chatRoomId: Int,
    ) : LetterIntent

    data class UpdateContents(val contents: String) : LetterIntent
    data object SendLetter : LetterIntent
    data object ClearError : LetterIntent
}
package com.nexters.emotia.feature.result.letter.contract

import com.nexters.emotia.core.domain.letter.entity.Letter

sealed interface LetterSideEffect {
    data class NavigateToResult(val letter: Letter) : LetterSideEffect
    data object NavigateToChatting : LetterSideEffect
    data class ShowError(val message: String) : LetterSideEffect
}
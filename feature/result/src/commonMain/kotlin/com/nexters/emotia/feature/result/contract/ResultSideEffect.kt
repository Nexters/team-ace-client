package com.nexters.emotia.feature.result.contract

sealed interface ResultSideEffect {
    data object NavigateToOnBoarding : ResultSideEffect
    data object NavigateToChatting : ResultSideEffect
    data class ShowError(val message: String) : ResultSideEffect
}
package com.nexters.emotia.feature.result.fairy.contract

sealed interface FairySideEffect {
    data object NavigateToOnBoarding : FairySideEffect
    data object NavigateToChatting : FairySideEffect
    data class ShowError(val message: String) : FairySideEffect
}
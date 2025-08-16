package com.nexters.emotia.feature.result.contract

sealed interface ResultIntent {
    data class InitializeFairy(
        val fairyId: Int,
        val fairyName: String,
        val fairySilhouetteImage: String,
    ) : ResultIntent

    data object StartExpandAnimation : ResultIntent
    data object NavigateToOnBoarding : ResultIntent
    data object NavigateToChatting : ResultIntent
}

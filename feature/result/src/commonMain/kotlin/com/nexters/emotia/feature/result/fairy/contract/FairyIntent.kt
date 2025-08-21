package com.nexters.emotia.feature.result.fairy.contract

sealed interface FairyIntent {
    data class InitializeFairy(
        val fairyId: Int,
        val fairyName: String,
        val fairySilhouetteImage: String,
    ) : FairyIntent

    data object StartExpandAnimation : FairyIntent
    data object NavigateToOnBoarding : FairyIntent
    data object NavigateToChatting : FairyIntent
}

package com.nexters.emotia.feature.result.fairy

import androidx.lifecycle.ViewModel
import com.nexters.emotia.feature.result.fairy.contract.FairyIntent
import com.nexters.emotia.feature.result.fairy.contract.FairySideEffect
import com.nexters.emotia.feature.result.fairy.contract.FairyState
import org.orbitmvi.orbit.Container
import org.orbitmvi.orbit.ContainerHost
import org.orbitmvi.orbit.viewmodel.container

class FairyViewModel : ViewModel(), ContainerHost<FairyState, FairySideEffect> {

    override val container: Container<FairyState, FairySideEffect> = container(FairyState())

    fun handleIntent(intent: FairyIntent) = intent {
        when (intent) {
            is FairyIntent.InitializeFairy -> {
                reduce {
                    state.copy(
                        fairyId = intent.fairyId,
                        fairyName = intent.fairyName,
                        fairyImage = intent.fairySilhouetteImage
                    )
                }
            }

            is FairyIntent.StartExpandAnimation -> {
                reduce {
                    state.copy(isExpanding = true)
                }
            }

            is FairyIntent.NavigateToOnBoarding -> {
                postSideEffect(FairySideEffect.NavigateToOnBoarding)
            }

            is FairyIntent.NavigateToChatting -> {
                postSideEffect(FairySideEffect.NavigateToChatting)
            }
        }
    }
}

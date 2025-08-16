package com.nexters.emotia.feature.result

import androidx.lifecycle.ViewModel
import com.nexters.emotia.feature.result.contract.ResultIntent
import com.nexters.emotia.feature.result.contract.ResultSideEffect
import com.nexters.emotia.feature.result.contract.ResultState
import org.orbitmvi.orbit.Container
import org.orbitmvi.orbit.ContainerHost
import org.orbitmvi.orbit.viewmodel.container

class ResultViewModel : ViewModel(), ContainerHost<ResultState, ResultSideEffect> {

    override val container: Container<ResultState, ResultSideEffect> = container(ResultState())

    fun handleIntent(intent: ResultIntent) = intent {
        when (intent) {
            is ResultIntent.InitializeFairy -> {
                reduce {
                    state.copy(
                        fairyId = intent.fairyId,
                        fairyName = intent.fairyName,
                        fairyImage = intent.fairyImage
                    )
                }
            }

            is ResultIntent.StartExpandAnimation -> {
                reduce {
                    state.copy(isExpanding = true)
                }
            }

            is ResultIntent.NavigateToOnBoarding -> {
                postSideEffect(ResultSideEffect.NavigateToOnBoarding)
            }

            is ResultIntent.NavigateToChatting -> {
                postSideEffect(ResultSideEffect.NavigateToChatting)
            }
        }
    }
}

package com.nexters.emotia.feature.result.letter

import androidx.lifecycle.ViewModel
import com.nexters.emotia.core.domain.letter.repository.LetterRepository
import com.nexters.emotia.feature.result.letter.contract.LetterIntent
import com.nexters.emotia.feature.result.letter.contract.LetterSideEffect
import com.nexters.emotia.feature.result.letter.contract.LetterState
import org.orbitmvi.orbit.Container
import org.orbitmvi.orbit.ContainerHost
import org.orbitmvi.orbit.viewmodel.container

class LetterViewModel(
    private val letterRepository: LetterRepository,
) : ViewModel(), ContainerHost<LetterState, LetterSideEffect> {

    override val container: Container<LetterState, LetterSideEffect> = container(LetterState())

    fun handleIntent(intent: LetterIntent) = intent {
        when (intent) {
            is LetterIntent.InitializeFairy -> {
                reduce {
                    state.copy(
                        fairyId = intent.fairyId,
                        fairyName = intent.fairyName,
                        fairyImage = intent.fairyImage,
                        chatRoomId = intent.chatRoomId
                    )
                }
            }

            is LetterIntent.UpdateContents -> {
                reduce {
                    state.copy(contents = intent.contents)
                }
            }

            is LetterIntent.SendLetter -> {
                sendLetter()
            }

            is LetterIntent.ClearError -> {
                reduce {
                    state.copy(error = null)
                }
            }
        }
    }

    private fun sendLetter() = intent {
        val currentState = state
        
        if (currentState.contents.isBlank()) return@intent

        reduce { state.copy(isLoading = true, error = null) }

        letterRepository.sendLetter(
            chatRoomId = currentState.chatRoomId,
            fairyId = currentState.fairyId,
            contents = currentState.contents
        )
            .onSuccess { letter ->
                reduce {
                    state.copy(isLoading = false)
                }
                postSideEffect(LetterSideEffect.NavigateToResult(letter))
            }
            .onFailure { exception ->
                val errorMessage = "편지 전송에 실패했습니다: ${exception.message}"
                reduce {
                    state.copy(
                        isLoading = false,
                        error = errorMessage
                    )
                }
                postSideEffect(LetterSideEffect.ShowError(errorMessage))
            }
    }
}
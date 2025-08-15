package com.nexters.emotia.feature.chatting

import androidx.lifecycle.ViewModel
import com.nexters.emotia.core.designsystem.component.BubbleType
import com.nexters.emotia.core.domain.chatting.repository.ChattingRepository
import com.nexters.emotia.feature.chatting.contract.ChattingIntent
import com.nexters.emotia.feature.chatting.contract.ChattingSideEffect
import com.nexters.emotia.feature.chatting.contract.ChattingState
import com.nexters.emotia.feature.chatting.model.ChatMessage
import com.nexters.emotia.feature.chatting.model.EmotionOption
import kotlinx.collections.immutable.PersistentList
import kotlinx.collections.immutable.persistentListOf
import kotlinx.collections.immutable.toPersistentList
import kotlinx.datetime.Clock
import org.orbitmvi.orbit.ContainerHost
import org.orbitmvi.orbit.viewmodel.container

class ChattingViewModel(
    private val chattingRepository: ChattingRepository,
) : ContainerHost<ChattingState, ChattingSideEffect>, ViewModel() {

    override val container = container<ChattingState, ChattingSideEffect>(
        initialState = ChattingState()
    )

    companion object {
        private const val MAX_USER_MESSAGE_COUNT = 3
        private const val MAX_CHAT_REACHED_MESSAGE = "아무래도 네 감정은..."
    }

    init {
        handleIntent(ChattingIntent.CreateChatRoom)
    }

    fun handleIntent(intent: ChattingIntent) {
        when (intent) {
            is ChattingIntent.CreateChatRoom -> createChatRoom()
            is ChattingIntent.InputTextChanged -> updateInputText(intent.text)
            is ChattingIntent.SendMessage -> sendMessage()
            is ChattingIntent.SelectEmotionOption -> selectEmotionOption(intent.option)
            is ChattingIntent.ClearError -> clearError()
            is ChattingIntent.LoadFairies -> loadFairies()
            is ChattingIntent.SelectFairy -> selectFairy(intent.index)
        }
    }

    private fun createChatRoom() = intent {
        reduce { state.copy(isLoading = true, error = null) }

        chattingRepository.createRoom("안드테스트")
            .onSuccess { chatRoom ->
                val firstMessage = ChatMessage(
                    text = chatRoom.firstMessage,
                    type = BubbleType.OTHER,
                    timestamp = Clock.System.now().toEpochMilliseconds()
                )

                reduce {
                    state.copy(
                        roomId = chatRoom.roomId,
                        messages = persistentListOf(firstMessage),
                        isLoading = false,
                        showEmotionChips = true,
                        emotionOptions = persistentListOf()
                    )
                }
            }
            .onFailure { exception ->
                val errorMessage = "채팅룸 생성에 실패했습니다: ${exception.message}"
                reduce {
                    state.copy(
                        isLoading = false,
                        error = errorMessage
                    )
                }
                postSideEffect(ChattingSideEffect.ShowError(errorMessage))
            }
    }

    private fun updateInputText(text: String) = intent {
        reduce { state.copy(currentInputText = text) }
    }

    private fun sendMessage() = intent {
        val currentState = state

        // TODO : 메시지 전송 중 send 연타 고려
        // TODO : 키보드 올렸을때 마지막 채팅이 위로 보이도록 개선
        if (currentState.currentInputText.isBlank() || currentState.roomId == null) return@intent

        val userMessage = ChatMessage(
            text = currentState.currentInputText,
            type = BubbleType.MINE,
            timestamp = Clock.System.now().toEpochMilliseconds()
        )

        val messageToSend = currentState.currentInputText
        val roomId = currentState.roomId

        reduce {
            state.copy(
                messages = state.messages.add(userMessage),
                currentInputText = "",
                isLoading = true
            )
        }

        chattingRepository.sendChat(roomId, messageToSend)
            .onSuccess { chatResponse ->
                val currentUserMessageCount = getUserMessageCount(state.messages)
                val aiResponseText = if (currentUserMessageCount >= MAX_USER_MESSAGE_COUNT) {
                    MAX_CHAT_REACHED_MESSAGE
                } else {
                    chatResponse.message
                }

                val aiResponse = ChatMessage(
                    text = chatResponse.message,
                    type = BubbleType.OTHER,
                    timestamp = Clock.System.now().toEpochMilliseconds()
                )

                reduce {
                    state.copy(
                        messages = state.messages.add(aiResponse),
                        isLoading = false,
                        error = null
                    )
                }

                // 최대 채팅 크기 도달 시 요정 정보 로드
                if (currentUserMessageCount >= MAX_USER_MESSAGE_COUNT) {
                    handleIntent(ChattingIntent.LoadFairies)
                }
            }
            .onFailure { exception ->
                val errorMessage = "메시지 전송에 실패했습니다: ${exception.message}"
                reduce {
                    state.copy(
                        isLoading = false,
                        error = errorMessage
                    )
                }
                postSideEffect(ChattingSideEffect.ShowError(errorMessage))
            }
    }

    private fun selectEmotionOption(option: EmotionOption) = intent {
        // TODO : 감정 옵션 칩 API 배포 후 구현
    }

    private fun clearError() = intent {
        reduce { state.copy(error = null) }
    }

    private fun loadFairies() = intent {
        val currentState = state
        if (currentState.roomId == null) return@intent

        chattingRepository.getFairies(currentState.roomId.toString())
            .onSuccess { fairies ->
                val initialIndex = if (fairies.size > 1) 1 else 0
                reduce {
                    state.copy(
                        fairies = fairies.toPersistentList(),
                        showFairyPager = true,
                        selectedFairyIndex = initialIndex
                    )
                }
            }
            .onFailure { exception ->
                val errorMessage = "요정 정보를 불러오는데 실패했습니다: ${exception.message}"
                reduce {
                    state.copy(error = errorMessage)
                }
                postSideEffect(ChattingSideEffect.ShowError(errorMessage))
            }
    }

    private fun selectFairy(index: Int) = intent {
        reduce { state.copy(selectedFairyIndex = index) }
    }

    private fun getUserMessageCount(messages: PersistentList<ChatMessage>): Int {
        return messages.count { it.type == BubbleType.MINE }
    }
}

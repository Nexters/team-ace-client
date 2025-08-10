package com.nexters.emotia.feature.chatting

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nexters.emotia.core.designsystem.component.BubbleType
import com.nexters.emotia.domain.chat.ChattingRepository
import kotlinx.collections.immutable.PersistentList
import kotlinx.collections.immutable.persistentListOf
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

data class ChatMessage(
    val text: String,
    val type: BubbleType,
)

data class EmotionOption(
    val text: String,
    val isSelected: Boolean = false
)

data class ChattingUiState(
    val messages: PersistentList<ChatMessage> = persistentListOf(),
    val currentInputText: String = "",
    val emotionOptions: PersistentList<EmotionOption> = persistentListOf(),
    val isTextFieldEnabled: Boolean = true,
    val showEmotionChips: Boolean = false,
    val isLoading: Boolean = false,
    val roomId: Int? = null,
    val error: String? = null
)

class ChattingViewModel(
    private val chattingRepository: ChattingRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(ChattingUiState())
    val uiState: StateFlow<ChattingUiState> = _uiState.asStateFlow()

    init {
        createChatRoom()
    }

    private fun createChatRoom() {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true, error = null) }

            chattingRepository.createRoom("hyeseon-dev")
                .onSuccess { chatRoom ->
                    val firstMessage = ChatMessage(
                        text = chatRoom.firstMessage,
                        type = BubbleType.OTHER
                    )

                    _uiState.update {
                        it.copy(
                            roomId = chatRoom.roomId,
                            messages = persistentListOf(firstMessage),
                            isLoading = false,
                            showEmotionChips = true,
                            // TODO : emotion chip 구현 필요
                            emotionOptions = persistentListOf()
                        )
                    }
                }
                .onFailure { exception ->
                    _uiState.update {
                        it.copy(
                            isLoading = false,
                            error = "채팅룸 생성에 실패했습니다: ${exception.message}"
                        )
                    }
                }
        }
    }

    fun onInputTextChanged(text: String) {
        _uiState.update { it.copy(currentInputText = text) }
    }

    fun onSendMessage() {
        val currentUiState = _uiState.value
        if (currentUiState.currentInputText.isBlank() || currentUiState.roomId == null) return

        val userMessage = ChatMessage(
            text = currentUiState.currentInputText,
            type = BubbleType.MINE
        )

        val messageToSend = currentUiState.currentInputText
        val roomId = currentUiState.roomId

        _uiState.update {
            it.copy(
                messages = it.messages.add(userMessage),
                currentInputText = "",
                isLoading = true
            )
        }

        sendChatMessage(roomId, messageToSend)
    }


    private fun sendChatMessage(roomId: Int?, message: String) {
        if (roomId == null) return

        viewModelScope.launch {
            delay(1000)

            chattingRepository.sendChat(roomId, message)
                .onSuccess { chatResponse ->
                    val aiResponse = ChatMessage(
                        text = chatResponse.message,
                        type = BubbleType.OTHER
                    )

                    _uiState.update {
                        it.copy(
                            messages = it.messages.add(aiResponse),
                            isLoading = false,
                            error = null
                        )
                    }
                }
                .onFailure { exception ->
                    _uiState.update {
                        it.copy(
                            isLoading = false,
                            error = "메시지 전송에 실패했습니다: ${exception.message}"
                        )
                    }
                }
        }
    }

}
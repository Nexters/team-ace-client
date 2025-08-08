package com.nexters.emotia.feature.chatting

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nexters.emotia.core.designsystem.component.BubbleType
import com.nexters.emotia.domain.chat.ChattingRepository
import com.nexters.emotia.domain.config.RemoteConfigRepository
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

data class ChatMessage(
    val text: String,
    val type: BubbleType,
)

data class EmotionOption(
    val text: String,
    val isSelected: Boolean = false,
)

data class ChattingUiState(
    val messages: List<ChatMessage> = emptyList(),
    val currentInputText: String = "",
    val emotionOptions: List<EmotionOption> = emptyList(),
    val isTextFieldEnabled: Boolean = true,
    val showEmotionChips: Boolean = false,
    val isLoading: Boolean = false,
    val roomId: Int? = null,
    val error: String? = null,
    val maxChatCount: Int = 5,
    val userMessageCount: Int = 0,
    val isMaxCountReached: Boolean = false,
)

class ChattingViewModel(
    private val chattingRepository: ChattingRepository,
    private val remoteConfigRepository: RemoteConfigRepository,
) : ViewModel() {

    var uiState by mutableStateOf(ChattingUiState())
        private set

    init {
        initializeRemoteConfig()
        createChatRoom()
    }

    private fun initializeRemoteConfig() {
        viewModelScope.launch {
            try {
                val maxCount = remoteConfigRepository.getChattingMaxCount()
                println("Max chat count: $maxCount")
                uiState = uiState.copy(maxChatCount = maxCount)
            } catch (e: Exception) {
                println("Failed to initialize remote config: ${e.message}")
            }
        }
    }

    private fun createChatRoom() {
        viewModelScope.launch {
            uiState = uiState.copy(isLoading = true, error = null)

            chattingRepository.createRoom("12345678")
                .onSuccess { chatRoom ->
                    val firstMessage = ChatMessage(
                        text = chatRoom.firstMessage,
                        type = BubbleType.OTHER
                    )

                    uiState = uiState.copy(
                        roomId = chatRoom.roomId,
                        messages = listOf(firstMessage),
                        isLoading = false,
                        showEmotionChips = true,
                        emotionOptions = getDefaultEmotions()
                    )
                }
                .onFailure { exception ->
                    uiState = uiState.copy(
                        isLoading = false,
                        error = "채팅룸 생성에 실패했습니다: ${exception.message}"
                    )
                }
        }
    }

    fun onInputTextChanged(text: String) {
        uiState = uiState.copy(currentInputText = text)
    }

    fun onSendMessage() {
        if (uiState.currentInputText.isBlank() || uiState.roomId == null) return

        val userMessage = ChatMessage(
            text = uiState.currentInputText,
            type = BubbleType.MINE
        )

        val messageToSend = uiState.currentInputText
        val roomId = uiState.roomId!!
        val newUserMessageCount = uiState.userMessageCount + 1

        uiState = uiState.copy(
            messages = uiState.messages + userMessage,
            currentInputText = "",
            isLoading = true,
            userMessageCount = newUserMessageCount,
        )

        sendChatMessage(
            roomId,
            messageToSend,
            isMaxCountReached = newUserMessageCount >= uiState.maxChatCount
        )
    }

    private fun sendChatMessage(roomId: Int, message: String, isMaxCountReached: Boolean = false) {
        viewModelScope.launch {
            // chatting typing indicator 를 보기 위해 딜레이 걸어둠
            kotlinx.coroutines.delay(1000)

            chattingRepository.sendChat(roomId, message)
                .onSuccess { chatResponse ->
                    val aiResponse = ChatMessage(
                        text = chatResponse.message,
                        type = BubbleType.OTHER
                    )

                    uiState = uiState.copy(
                        messages = uiState.messages + aiResponse,
                        isLoading = false,
                        error = null,
                    )

                    if (isMaxCountReached) {
                        delay(2000)
                        uiState = uiState.copy(isMaxCountReached = true)
                    }
                }
                .onFailure { exception ->
                    uiState = uiState.copy(
                        isLoading = false,
                        error = "메시지 전송에 실패했습니다: ${exception.message}"
                    )
                }

        }
    }

    private fun getDefaultEmotions() = listOf(
        EmotionOption(text = "기쁨"),
        EmotionOption(text = "슬픔"),
        EmotionOption(text = "화남"),
    )
}

package com.nexters.emotia.feature.chatting

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nexters.emotia.core.designsystem.component.BubbleType
import com.nexters.emotia.domain.chat.ChattingRepository
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
    val messages: List<ChatMessage> = emptyList(),
    val currentInputText: String = "",
    val emotionOptions: List<EmotionOption> = emptyList(),
    val isTextFieldEnabled: Boolean = true,
    val showEmotionChips: Boolean = false,
    val isLoading: Boolean = false,
    val roomId: Int? = null,
    val error: String? = null
)

class ChattingViewModel(
    private val chattingRepository: ChattingRepository
) : ViewModel() {

    var uiState by mutableStateOf(ChattingUiState())
        private set

    init {
        createChatRoom()
    }

    private fun createChatRoom() {
        viewModelScope.launch {
            uiState = uiState.copy(isLoading = true, error = null)

            chattingRepository.createRoom("유저네임하드코딩해야됨각자")
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

        uiState = uiState.copy(
            messages = uiState.messages + userMessage,
            currentInputText = "",
            isLoading = true
        )

        // 지금은 임시 응답으로 처리
        simulateAIResponse()
    }


    private fun simulateAIResponse() {
        viewModelScope.launch {
            // chatting typing indicator 를 보기 위해 딜레이 걸어둠
            kotlinx.coroutines.delay(1000)

            val responses = listOf(
                "그렇게 느끼시는군요. 더 자세히 말씀해주세요.",
                "이해합니다. 그런 감정이 드는 이유가 있을까요?",
                "흥미로운 이야기네요. 어떤 기분이신가요?",
                "힘든 하루를 보냈다니 안타깝네요. 어떤 일이 있었나요?",
                "그런 기분이 드셨군요. 어떤 상황에서 그런 감정을 느꼈나요?",
                "그런 감정을 느끼셨군요. 어떤 일이 있었나요?"
            )

            val aiResponse = ChatMessage(
                text = responses.random(),
                type = BubbleType.OTHER
            )

            uiState = uiState.copy(
                messages = uiState.messages + aiResponse,
                isLoading = false
            )
        }
    }

    private fun getDefaultEmotions() = listOf(
        EmotionOption(text = "기쁨"),
        EmotionOption(text = "슬픔"),
        EmotionOption(text = "화남"),
    )
}
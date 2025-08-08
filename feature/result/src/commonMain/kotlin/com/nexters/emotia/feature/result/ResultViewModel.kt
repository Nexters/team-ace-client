package com.nexters.emotia.feature.result

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nexters.emotia.domain.chat.ChattingRepository
import com.nexters.emotia.domain.chat.entity.Fairy
import kotlinx.coroutines.launch

data class ResultUiState(
    val fairies: List<Fairy> = emptyList(),
    val isLoading: Boolean = false,
    val error: String? = null
)

class ResultViewModel(
    private val chattingRepository: ChattingRepository
) : ViewModel() {

    var uiState by mutableStateOf(ResultUiState())
        private set

    fun loadFairies(chatRoomId: String) {
        viewModelScope.launch {
            uiState = uiState.copy(isLoading = true, error = null)

            chattingRepository.getFairies(chatRoomId)
                .onSuccess { fairies ->
                    uiState = uiState.copy(
                        fairies = fairies,
                        isLoading = false,
                        error = null
                    )
                }
                .onFailure { exception ->
                    uiState = uiState.copy(
                        isLoading = false,
                        error = "요정 정보를 불러오는데 실패했습니다: ${exception.message}"
                    )
                }
        }
    }
}
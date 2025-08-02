package com.nexters.ace.feature.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nexters.ace.domain.chat.ChattingRepository
import com.nexters.ace.domain.chat.entity.ChattingRoom
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class MainViewModel(
    private val ipInfoRepository: ChattingRepository
) : ViewModel() {

    private val _ipInfoText = MutableStateFlow("Loading ~ ")
    val ipInfoText: StateFlow<String> = _ipInfoText.asStateFlow()

    init {
        loadIpInfo()
    }

    private fun loadIpInfo() {
        viewModelScope.launch {
            _ipInfoText.value = "Loading ~ "
            ipInfoRepository.createRoom()
                ?.onSuccess { ipInfo ->
                    _ipInfoText.value = formatIpInfo(ipInfo)
                }
                ?.onFailure { throwable ->
                    _ipInfoText.value = "Error: ${throwable.message ?: "Unknown error"}"
                }
        }
    }

    private fun formatIpInfo(ipInfo: ChattingRoom): String {
        return """
            IP Address: ${ipInfo.ipAddress}
            Status: ${ipInfo.status}
            Country: ${ipInfo.country}
            City: ${ipInfo.city}
            Latitude: ${ipInfo.latitude}
            Longitude: ${ipInfo.longitude}
        """.trimIndent()
    }
}
package com.nexters.emotia.feature.result.contract

data class ResultState(
    val fairyId: Int = 0,
    val fairyName: String = "",
    val fairyImage: String = "",
    val isExpanding: Boolean = false,
    val isLoading: Boolean = false,
    val error: String? = null
)
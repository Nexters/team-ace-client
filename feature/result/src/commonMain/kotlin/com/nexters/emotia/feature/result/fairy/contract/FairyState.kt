package com.nexters.emotia.feature.result.fairy.contract

data class FairyState(
    val fairyId: Int = 0,
    val fairyName: String = "",
    val fairyImage: String = "",
    val fairySilhouetteImage: String = "",
    val isExpanding: Boolean = false,
    val isLoading: Boolean = false,
    val error: String? = null,
)

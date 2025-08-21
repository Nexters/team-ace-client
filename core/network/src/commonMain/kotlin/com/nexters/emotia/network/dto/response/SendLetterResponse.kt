package com.nexters.emotia.network.dto.response

import kotlinx.serialization.Serializable

@Serializable
data class SendLetterResponse(
    val success: Boolean,
    val data: LetterData?,
    val error: String?,
)

@Serializable
data class LetterData(
    val fairyId: Int,
    val name: String,
    val image: String,
    val contents: String,
)
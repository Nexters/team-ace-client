package com.nexters.emotia.network.dto.response

import kotlinx.serialization.Serializable

@Serializable
data class GetFairiesResponse(
    val success: Boolean,
    val data: GetFairiesData?,
    val error: String?,
)

@Serializable
data class GetFairiesData(
    val fairies: List<FairyDto>,
)

@Serializable
data class FairyDto(
    val id: Int,
    val name: String,
    val image: String,
    val silhouetteImage: String,
    val emotion: String,
    val emotionDescription: String,
)

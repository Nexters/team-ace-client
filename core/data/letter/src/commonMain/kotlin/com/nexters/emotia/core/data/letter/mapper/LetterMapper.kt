package com.nexters.emotia.core.data.letter.mapper

import com.nexters.emotia.core.domain.letter.entity.Letter
import com.nexters.emotia.network.dto.response.SendLetterResponse

fun SendLetterResponse.toDomain(): Letter {
    return Letter(
        fairyId = data?.fairyId ?: 0,
        name = data?.name ?: "",
        image = data?.image ?: "",
        contents = data?.contents ?: ""
    )
}
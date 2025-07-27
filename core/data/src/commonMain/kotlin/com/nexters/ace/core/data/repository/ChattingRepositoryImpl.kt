package com.nexters.ace.core.data.repository

import com.nexters.ace.domain.di.ChattingRepsitory
import com.nexters.ace.domain.entity.ChattingRoom

class ChattingRepositoryImpl(

) :  ChattingRepsitory{

    override suspend fun createRoom(): Result<ChattingRoom>? {
        TODO("Not yet implemented")
    }
}

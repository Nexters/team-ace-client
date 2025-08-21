package com.nexters.emotia.core.data.letter.di

import com.nexters.emotia.core.data.letter.datasource.LetterRemoteDataSource
import com.nexters.emotia.core.data.letter.datasource.LetterRemoteDataSourceImpl
import com.nexters.emotia.core.data.letter.repository.LetterRepositoryImpl
import com.nexters.emotia.core.domain.letter.repository.LetterRepository
import com.nexters.emotia.network.service.LetterApiService
import org.koin.dsl.module

val dataLetterModule = module {
    single<LetterApiService> { LetterApiService(get()) }
    single<LetterRemoteDataSource> { LetterRemoteDataSourceImpl(get()) }
    single<LetterRepository> { LetterRepositoryImpl(get()) }
}
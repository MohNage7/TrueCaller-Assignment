package com.mohnage7.truecaller.di

import com.mohnage7.data.BlogRepositoryImpl
import com.mohnage7.domain.BlogRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
interface DataModule {

    @Binds
    fun bindsBlogRepository(
        clogRepositoryImpl: BlogRepositoryImpl
    ): BlogRepository

}

package com.eneskayiklik.eventverse.di

import com.eneskayiklik.eventverse.data.repository.polls.CreatePollRepositoryImpl
import com.eneskayiklik.eventverse.data.repository.share.ShareRepositoryImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object CreateModule {

    @Singleton
    @Provides
    fun provideShareRepository(): ShareRepositoryImpl = ShareRepositoryImpl()

    @Singleton
    @Provides
    fun provideCreatePollRepository(): CreatePollRepositoryImpl = CreatePollRepositoryImpl()
}
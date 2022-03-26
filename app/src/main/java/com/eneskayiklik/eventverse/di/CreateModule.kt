package com.eneskayiklik.eventverse.di

import com.eneskayiklik.eventverse.feature_create.data.repository.CreateEventRepositoryImpl
import com.eneskayiklik.eventverse.feature_create.domain.repository.CreateRepository
import com.eneskayiklik.eventverse.feature_share.data.repository.ShareRepositoryImpl
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
    fun provideCreateRepository(): CreateRepository = CreateEventRepositoryImpl()

    @Singleton
    @Provides
    fun provideShareRepository(): ShareRepositoryImpl = ShareRepositoryImpl()
}
package com.eneskayiklik.eventverse.di

import com.eneskayiklik.eventverse.feature_auth.data.repository.AuthRepositoryImpl
import com.eneskayiklik.eventverse.feature_auth.domain.repository.AuthRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AuthModule {

    @Singleton
    @Provides
    fun provideAuthRepository(): AuthRepository = AuthRepositoryImpl()
}
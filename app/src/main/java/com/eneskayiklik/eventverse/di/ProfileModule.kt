package com.eneskayiklik.eventverse.di

import com.eneskayiklik.eventverse.feature_profile.data.repository.ProfileRepositoryImpl
import com.eneskayiklik.eventverse.feature_settings.data.repository.SettingsRepositoryImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ProfileModule {

    @Singleton
    @Provides
    fun provideProfileRepository(): ProfileRepositoryImpl = ProfileRepositoryImpl()

    @Singleton
    @Provides
    fun provideSettingsRepository(): SettingsRepositoryImpl = SettingsRepositoryImpl()
}
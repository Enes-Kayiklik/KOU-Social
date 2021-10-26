package com.eneskayiklik.eventverse.di

import com.eneskayiklik.eventverse.feature_explore.data.repository.ExploreRepositoryImpl
import com.eneskayiklik.eventverse.feature_explore.domain.repository.ExploreRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ExploreModule {

    @Singleton
    @Provides
    fun provideExploreRepository(): ExploreRepository = ExploreRepositoryImpl()
}
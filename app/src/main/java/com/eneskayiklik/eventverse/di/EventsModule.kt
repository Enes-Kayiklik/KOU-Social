package com.eneskayiklik.eventverse.di

import com.eneskayiklik.eventverse.data.repository.events.EventsRepositoryImpl
import com.eneskayiklik.eventverse.data.repository.explore.ExploreRepositoryImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object EventsModule {

    @Provides
    @Singleton
    fun provideEventsRepository(): EventsRepositoryImpl = EventsRepositoryImpl()
}
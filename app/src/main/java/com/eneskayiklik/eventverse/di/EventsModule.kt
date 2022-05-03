package com.eneskayiklik.eventverse.di

import com.eneskayiklik.eventverse.data.repository.events.EventDetailRepositoryImpl
import com.eneskayiklik.eventverse.data.repository.events.EventsRepositoryImpl
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

    @Provides
    @Singleton
    fun provideEventDetailRepository(): EventDetailRepositoryImpl = EventDetailRepositoryImpl()
}
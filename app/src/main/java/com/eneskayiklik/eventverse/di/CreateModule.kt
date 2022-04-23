package com.eneskayiklik.eventverse.di

import android.content.Context
import com.eneskayiklik.eventverse.data.repository.polls.CreatePollRepositoryImpl
import com.eneskayiklik.eventverse.data.repository.polls.PollsRepositoryImpl
import com.eneskayiklik.eventverse.data.repository.share.ShareRepositoryImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object CreateModule {

    @Singleton
    @Provides
    fun provideShareRepository(
        @ApplicationContext context: Context
    ): ShareRepositoryImpl = ShareRepositoryImpl(context = context)

    @Singleton
    @Provides
    fun provideCreatePollRepository(): CreatePollRepositoryImpl = CreatePollRepositoryImpl()

    @Singleton
    @Provides
    fun providePollsRepository(): PollsRepositoryImpl = PollsRepositoryImpl()
}
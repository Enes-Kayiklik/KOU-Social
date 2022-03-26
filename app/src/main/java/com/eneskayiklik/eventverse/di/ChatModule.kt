package com.eneskayiklik.eventverse.di

import android.content.Context
import com.eneskayiklik.eventverse.feature_auth.data.repository.StreamRepositoryImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import io.getstream.chat.android.client.ChatClient
import io.getstream.chat.android.client.logger.ChatLogLevel
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object ChatModule {

    @Singleton
    @Provides
    fun provideStreamRepository(
        chatClient: ChatClient
    ): StreamRepositoryImpl = StreamRepositoryImpl(chatClient)

    @Provides
    @Singleton
    fun provideChatClient(@ApplicationContext context: Context): ChatClient =
        ChatClient.Builder("8zwvznavfus8", context)
            .logLevel(ChatLogLevel.ALL) // Set to NOTHING in prod
            .build()
}
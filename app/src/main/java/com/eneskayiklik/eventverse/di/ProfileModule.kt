package com.eneskayiklik.eventverse.di

import android.content.Context
import com.eneskayiklik.eventverse.data.repository.profile.*
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
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

    @Singleton
    @Provides
    fun provideEditProfileRepository(
        @ApplicationContext context: Context
    ): EditProfileRepositoryImpl = EditProfileRepositoryImpl(context = context)

    @Singleton
    @Provides
    fun provideDepartmentRepository(): DepartmentRepositoryImpl = DepartmentRepositoryImpl()

    @Singleton
    @Provides
    fun provideUpdatePasswordRepository(): UpdatePasswordRepositoryImpl = UpdatePasswordRepositoryImpl()

    @Singleton
    @Provides
    fun provideDeleteAccountRepository(): DeleteAccountRepositoryImpl = DeleteAccountRepositoryImpl()

    @Singleton
    @Provides
    fun verifyAccountRepository(): VerifyAccountRepositoryImpl = VerifyAccountRepositoryImpl()
}
package com.eneskayiklik.eventverse.di

import com.eneskayiklik.eventverse.BuildConfig
import com.eneskayiklik.eventverse.data.remote.meal.MealAPI
import com.eneskayiklik.eventverse.data.repository.meal.MealRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object MealApiModule {

    @Singleton
    @Provides
    fun provideRetrofit(): Retrofit = Retrofit.Builder()
        .baseUrl(BuildConfig.BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    @Singleton
    @Provides
    fun provideMealApi(
        retrofit: Retrofit
    ): MealAPI = retrofit.create(MealAPI::class.java)

    @Singleton
    @Provides
    fun provideMealRepository(
        mealAPI: MealAPI
    ): MealRepository = MealRepository(mealAPI)
}
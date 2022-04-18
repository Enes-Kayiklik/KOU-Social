package com.eneskayiklik.eventverse.data.remote.meal

import com.eneskayiklik.eventverse.data.model.meal.MealData
import retrofit2.http.GET
import retrofit2.http.Path

interface MealAPI {

    @GET("{path}")
    suspend fun getMealData(@Path("path") path: String): MealData
}
package com.eneskayiklik.eventverse.feature_meal.domain.gateway

import com.eneskayiklik.eventverse.feature_meal.data.model.meal_response.MealData
import retrofit2.http.GET
import retrofit2.http.Path

interface MealAPI {

    @GET("{path}")
    suspend fun getMealData(@Path("path") path: String): MealData
}
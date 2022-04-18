package com.eneskayiklik.eventverse.data.repository.meal

import com.eneskayiklik.eventverse.BuildConfig
import com.eneskayiklik.eventverse.util.Resource
import com.eneskayiklik.eventverse.data.remote.meal.MealAPI
import kotlinx.coroutines.flow.flow
import java.lang.Exception
import javax.inject.Inject

class MealRepository @Inject constructor(
    private val mealApi: MealAPI
) {

    suspend fun getMealData() = flow {
        try {
            emit(Resource.Loading())
            val data = mealApi.getMealData(BuildConfig.API_PATH)
            emit(Resource.Success(data))
        } catch (e: Exception) {
            e.printStackTrace()
            emit(Resource.Error(e.message ?: ""))
        }
    }
}
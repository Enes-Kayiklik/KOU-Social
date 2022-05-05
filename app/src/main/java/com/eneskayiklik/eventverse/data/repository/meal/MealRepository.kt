package com.eneskayiklik.eventverse.data.repository.meal

import androidx.core.os.bundleOf
import com.eneskayiklik.eventverse.BuildConfig
import com.eneskayiklik.eventverse.util.Resource
import com.eneskayiklik.eventverse.data.remote.meal.MealAPI
import com.eneskayiklik.eventverse.util.Settings
import com.google.firebase.analytics.FirebaseAnalytics
import com.google.firebase.analytics.ktx.analytics
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.flow.flow
import java.lang.Exception

class MealRepository(
    private val mealApi: MealAPI,
    private val analytics: FirebaseAnalytics = Firebase.analytics
) {

    suspend fun getMealData() = flow {
        try {
            emit(Resource.Loading())
            val data = mealApi.getMealData(BuildConfig.API_PATH)
            analytics.logEvent(
                "meal_screen", bundleOf(
                    "user" to Settings.currentUser.userId
                )
            )
            emit(Resource.Success(data))
        } catch (e: Exception) {
            e.printStackTrace()
            emit(Resource.Error(e.message ?: ""))
        }
    }
}
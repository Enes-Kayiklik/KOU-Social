package com.eneskayiklik.eventverse.feature_meal.data.state

import com.eneskayiklik.eventverse.feature_meal.data.model.meal_response.Meal

data class MealScreenState(
    val isLoading: Boolean = false,
    val hasError: Boolean = false,
    val errorTitle: String = "",
    val errorSubtitle: String = "",
    val mealList: List<Meal> = emptyList(),
    val availableDates: List<AvailableDate> = emptyList(),
    val initialPage: Int = 0
)

data class AvailableDate(
    val date: String,
    val day: String
)

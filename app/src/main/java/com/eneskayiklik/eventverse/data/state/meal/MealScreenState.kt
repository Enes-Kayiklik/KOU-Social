package com.eneskayiklik.eventverse.data.state.meal

import com.eneskayiklik.eventverse.data.model.meal.Meal

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

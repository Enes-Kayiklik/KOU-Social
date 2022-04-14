package com.eneskayiklik.eventverse.feature_meal.data.model.meal_response

data class MealData(
    val errorSubtitle: String = "",
    val errorTitle: String = "",
    val hasError: Boolean = false,
    val mealList: List<Meal> = emptyList()
)
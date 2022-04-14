package com.eneskayiklik.eventverse.feature_meal.data.model.meal_response

import java.text.SimpleDateFormat
import java.util.*

data class Meal(
    val date: String = "",
    val day: String = "",
    val mainDish: List<Dish> = emptyList(),
    val side: Dish = Dish(),
    val sideDish: Dish = Dish(),
    val soup: Dish = Dish()
) {
    fun getFormattedDate(): String {
        val formatter = SimpleDateFormat("dd.MM.yyyy", Locale.getDefault())
        val newDate = formatter.parse(date) ?: return ""
        return SimpleDateFormat("EEEE, dd MMM", Locale.getDefault()).format(newDate)
    }

    fun getShareMealDate(): String {
        val formatter = SimpleDateFormat("dd.MM.yyyy", Locale.getDefault())
        val newDate = formatter.parse(date) ?: return ""
        return SimpleDateFormat("dd MMMM EEEE", Locale.getDefault()).format(newDate)
    }
}
package com.eneskayiklik.eventverse.data.model.meal

import java.text.SimpleDateFormat
import java.util.*

data class MealData(
    val errorSubtitle: String = "",
    val errorTitle: String = "",
    val hasError: Boolean = false,
    val mealList: List<Meal> = emptyList()
)

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

data class Dish(
    val calorie: String = "",
    val image: String = "",
    val name: String = ""
)
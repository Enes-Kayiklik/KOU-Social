package com.eneskayiklik.eventverse.core.util.extension

import android.content.Context
import android.content.Intent
import com.eneskayiklik.eventverse.BuildConfig
import com.eneskayiklik.eventverse.feature_meal.data.model.meal_response.Meal
import java.lang.Exception


fun Context.shareAppLink() {
    try {
        Intent(Intent.ACTION_SEND).apply {
            type = "text/plain"
            putExtra(Intent.EXTRA_SUBJECT, "Share Application")
            val appLink =
                "https://play.google.com/store/apps/details?id=${BuildConfig.APPLICATION_ID}"
            putExtra(Intent.EXTRA_TEXT, appLink)
            startActivity(this)
        }
    } catch (e: Exception) {
        e.printStackTrace()
    }
}

fun Context.shareMeal(meal: Meal) {
    val mealList ="""
        ${meal.getShareMealDate()} Yemek Listesi
        
        Çorba
        - ${meal.soup.name} -- ${meal.soup.calorie} kalori
        
        Ana Yemek
        - ${meal.mainDish.first().name} -- ${meal.mainDish.first().calorie} kalori
        - ${meal.mainDish.last().name} -- ${meal.mainDish.last().calorie} kalori
        
        Yan Yemek
        - ${meal.sideDish.name} -- ${meal.sideDish.calorie} kalori
        
        Extra
        - ${meal.side.name} -- ${meal.side.calorie} kalori
    """.trimIndent()
    try {
        Intent(Intent.ACTION_SEND).apply {
            type = "text/plain"
            putExtra(Intent.EXTRA_SUBJECT, "My application name")
            putExtra(Intent.EXTRA_TEXT, mealList)
            startActivity(this)
        }
    } catch (e: Exception) {
        e.printStackTrace()
    }
}
package com.eneskayiklik.eventverse.feature_meal.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.eneskayiklik.eventverse.core.util.Resource
import com.eneskayiklik.eventverse.feature_meal.data.model.meal_response.Meal
import com.eneskayiklik.eventverse.feature_meal.data.repository.MealRepository
import com.eneskayiklik.eventverse.feature_meal.data.state.AvailableDate
import com.eneskayiklik.eventverse.feature_meal.data.state.MealScreenState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Inject

@HiltViewModel
class MealViewModel @Inject constructor(
    private val mealRepository: MealRepository
) : ViewModel() {
    private val _state = MutableStateFlow(MealScreenState())
    val state: StateFlow<MealScreenState> = _state

    init {
        getMealData()
    }

    private fun getMealData() {
        viewModelScope.launch(Dispatchers.IO) {
            mealRepository.getMealData().collectLatest {
                when (it) {
                    is Resource.Error -> _state.value = _state.value.copy(
                        isLoading = false,
                        hasError = true
                    )
                    is Resource.Loading -> _state.value = _state.value.copy(
                        isLoading = true,
                    )
                    is Resource.Success -> _state.value = _state.value.copy(
                        isLoading = false,
                        hasError = it.data.hasError,
                        errorSubtitle = it.data.errorSubtitle,
                        errorTitle = it.data.errorTitle,
                        mealList = it.data.mealList,
                        availableDates = it.data.mealList.map { d ->
                            AvailableDate(
                                day = d.day.substring(0, 3).uppercase(),
                                date = d.date.substringBefore(".")
                            )
                        }, initialPage = calculateInitialPage(it.data.mealList)
                    )
                }
            }
        }
    }

    private fun calculateInitialPage(mealList: List<Meal>): Int {
        val today = SimpleDateFormat("dd.MM.yyyy", Locale.getDefault()).format(Date(System.currentTimeMillis()))
        return mealList.indexOfFirst { it.date == today }.takeIf { it >= 0 } ?: 0
    }
}
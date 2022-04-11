package com.eneskayiklik.eventverse.feature_meal.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.eneskayiklik.eventverse.core.util.MEAL_LINK
import com.eneskayiklik.eventverse.core.util.extension.getMealUrl
import com.eneskayiklik.eventverse.feature_meal.data.state.MealScreenState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import org.jsoup.Jsoup
import javax.inject.Inject

@HiltViewModel
class MealViewModel @Inject constructor(

) : ViewModel() {
    private val _state = MutableStateFlow(MealScreenState())
    val state: StateFlow<MealScreenState> = _state

    init {
        getMealConnectionUrl()
    }

    private fun getMealConnectionUrl() {
        viewModelScope.launch(Dispatchers.IO) {
            val doc = Jsoup.connect(MEAL_LINK).get()
            val link = doc.body().select("tbody > tr").last()?.select("dd")?.last()?.select("a")
                ?.last()?.attr("href")?.getMealUrl() ?: ""
            _state.value = _state.value.copy(connectionUrl = link)
        }
    }
}
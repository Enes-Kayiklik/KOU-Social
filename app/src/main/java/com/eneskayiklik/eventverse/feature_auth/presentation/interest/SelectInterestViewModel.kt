package com.eneskayiklik.eventverse.feature_auth.presentation.interest

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.eneskayiklik.eventverse.core.util.Screen
import com.eneskayiklik.eventverse.core.util.UiEvent
import com.eneskayiklik.eventverse.feature_auth.domain.model.InterestModel
import com.eneskayiklik.eventverse.feature_auth.domain.use_case.SelectInterestUseCase
import com.eneskayiklik.eventverse.feature_auth.presentation.interest.util.SelectInterestEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SelectInterestViewModel @Inject constructor(
    private val interestUseCase: SelectInterestUseCase
) : ViewModel() {
    private val _interests = MutableStateFlow<List<InterestModel>>(emptyList())
    val interests: StateFlow<List<InterestModel>> = _interests

    private val _isSelectClicked = MutableStateFlow(false)
    val isSelectClicked: StateFlow<Boolean> = _isSelectClicked

    private val _uiState = MutableSharedFlow<UiEvent>()
    val uiState: SharedFlow<UiEvent> = _uiState

    init {
        getInterests()
    }

    fun onEvent(event: SelectInterestEvent) {
        when (event) {
            SelectInterestEvent.OnSelectInterest -> selectInterests()
        }
    }

    private fun selectInterests() {
        viewModelScope.launch {
            _isSelectClicked.value = _isSelectClicked.value.not()
            val result = interestUseCase.setInterests(_interests.value)
            if (result) {
                _isSelectClicked.value = false
                _uiState.emit(UiEvent.Navigate(Screen.Timeline.route))
            }
        }
    }

    private fun getInterests() {
        viewModelScope.launch {
            _interests.value = interestUseCase.getInterests()
        }
    }

    fun onSelectInterest(id: Int) {
        val newList = _interests.value.toMutableList()
        val index = newList.indexOfFirst { it.id == id }
        if (index != -1) {
            val data = newList[index].copy(isSelected = newList[index].isSelected.not())
            newList.removeAt(index)
            newList.add(index, data)
            _interests.value = newList
        }
    }
}
package com.eneskayiklik.eventverse.feature_explore.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.eneskayiklik.eventverse.feature_explore.domain.model.UpcomingEventModel
import com.eneskayiklik.eventverse.feature_explore.domain.use_case.ExploreUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ExploreViewModel @Inject constructor(
    private val exploreUseCase: ExploreUseCase
) : ViewModel(

) {
    private val _upcomingEvents = MutableStateFlow<List<UpcomingEventModel>>(emptyList())
    val upcomingEvents: StateFlow<List<UpcomingEventModel>> = _upcomingEvents

    init {
        getUpcomingEvents()
    }

    private fun getUpcomingEvents() {
        viewModelScope.launch {
            _upcomingEvents.value = exploreUseCase.getUpcomingEvents()
        }
    }
}
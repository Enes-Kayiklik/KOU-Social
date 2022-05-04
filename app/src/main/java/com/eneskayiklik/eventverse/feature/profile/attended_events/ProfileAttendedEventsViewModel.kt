package com.eneskayiklik.eventverse.feature.profile.attended_events

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.eneskayiklik.eventverse.data.repository.profile.ProfileAttendedEventsRepositoryImpl
import com.eneskayiklik.eventverse.data.state.events.EventsState
import com.eneskayiklik.eventverse.util.Resource
import com.eneskayiklik.eventverse.util.UiEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProfileAttendedEventsViewModel @Inject constructor(
    private val eventsRepository: ProfileAttendedEventsRepositoryImpl,
    _stateHandle: SavedStateHandle
) : ViewModel() {

    private val _state = MutableStateFlow(EventsState())
    val state: StateFlow<EventsState> = _state

    private val _event = MutableSharedFlow<UiEvent>()
    val event: SharedFlow<UiEvent> = _event

    private val _getNext: Boolean
        get() = _state.value.isLoading.not() && _state.value.hasNext

    private var userId: String = _stateHandle.get<String>("userId") ?: ""

    init {
        getEvents()
    }

    private fun getEvents() = viewModelScope.launch(Dispatchers.IO) {
        eventsRepository.getEvents(_getNext, userId).collectLatest {
            when (it) {
                is Resource.Error -> _state.value = _state.value.copy(
                    isLoading = false,
                    hasNext = false
                )
                is Resource.Loading -> _state.value = _state.value.copy(
                    isLoading = true
                )
                is Resource.Success -> _state.value = _state.value.copy(
                    isLoading = false,
                    events = _state.value.events.plus(it.data),
                    hasNext = false
                )
            }
        }
    }
}
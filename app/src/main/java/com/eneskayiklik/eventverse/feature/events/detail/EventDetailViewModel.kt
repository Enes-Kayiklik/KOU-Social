package com.eneskayiklik.eventverse.feature.events.detail

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.eneskayiklik.eventverse.data.repository.events.EventDetailRepositoryImpl
import com.eneskayiklik.eventverse.util.Resource
import com.eneskayiklik.eventverse.util.UiEvent
import com.eneskayiklik.eventverse.data.state.events.EventDetailState
import com.eneskayiklik.eventverse.util.extension.formatDate
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import java.util.*
import javax.inject.Inject

@HiltViewModel
class EventDetailViewModel @Inject constructor(
    _stateHandle: SavedStateHandle,
    private val eventDetailRepository: EventDetailRepositoryImpl
) : ViewModel() {
    private val _state = MutableStateFlow(EventDetailState())
    val state: StateFlow<EventDetailState> = _state

    private val _event = MutableSharedFlow<UiEvent>()
    val event: SharedFlow<UiEvent> = _event

    init {
        val eventId = _stateHandle.get<String>("eventId") ?: ""
        getEventDetail(eventId)
    }

    private fun getEventDetail(eventId: String) = viewModelScope.launch(Dispatchers.IO) {
        eventDetailRepository.getEventDetail(eventId).collectLatest {
            if (it.response == null) {
                _event.emit(UiEvent.ClearBackStack)
                return@collectLatest
            }

            when (it) {
                is Resource.Error -> Unit
                is Resource.Loading -> _state.value = _state.value.copy(
                    isLoading = true
                )
                is Resource.Success -> {
                    _state.value = _state.value.copy(
                        isLoading = false,
                        event = it.data
                    )
                    startDateCounter(it.data?.startTime ?: return@collectLatest)
                }
            }
        }
    }

    private fun startDateCounter(initial: Date) = viewModelScope.launch(Dispatchers.IO) {
        while (true) {
            val remaining = initial.time - System.currentTimeMillis()
            _state.value =_state.value.copy(
                date = remaining.formatDate()
            )
            delay(1000L)
        }
    }
}
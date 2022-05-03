package com.eneskayiklik.eventverse.feature.events.list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.eneskayiklik.eventverse.data.repository.events.EventsRepositoryImpl
import com.eneskayiklik.eventverse.data.state.events.EventsState
import com.eneskayiklik.eventverse.util.POST_PAGE_SIZE
import com.eneskayiklik.eventverse.util.Resource
import com.eneskayiklik.eventverse.util.UiEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class EventsViewModel @Inject constructor(
    private val eventsRepository: EventsRepositoryImpl
): ViewModel() {

    private val _state = MutableStateFlow(EventsState())
    val state: StateFlow<EventsState> = _state

    private val _event = MutableSharedFlow<UiEvent>()
    val event: SharedFlow<UiEvent> = _event

    private val _getNext: Boolean
        get() = _state.value.isLoading.not() && _state.value.hasNext

    private val _canRefresh: Boolean
        get() = _state.value.isLoading.not()

    init {
        getEvents()
    }

    fun getEvents() = viewModelScope.launch(Dispatchers.IO) {
        eventsRepository.getEvents(_getNext).collectLatest {
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
                    hasNext = it.data.count() > POST_PAGE_SIZE - 1
                )
            }
        }
    }

    fun refreshPosts() = viewModelScope.launch(Dispatchers.IO) {
        eventsRepository.getEvents(_canRefresh, true).collectLatest {
            when (it) {
                is Resource.Error -> _state.value = _state.value.copy(
                    isLoading = false,
                    isRefreshing = false,
                    hasNext = false
                )
                is Resource.Loading -> _state.value = _state.value.copy(
                    isLoading = false,
                    isRefreshing = true,
                )
                is Resource.Success -> _state.value = _state.value.copy(
                    isLoading = false,
                    isRefreshing = false,
                    events = it.data,
                    hasNext = it.data.count() > POST_PAGE_SIZE - 1
                )
            }
        }
    }
}
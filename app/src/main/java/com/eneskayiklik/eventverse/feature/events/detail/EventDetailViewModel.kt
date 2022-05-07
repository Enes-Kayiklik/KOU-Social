package com.eneskayiklik.eventverse.feature.events.detail

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.eneskayiklik.eventverse.data.event.auth.event_detail.EventDetailEvent
import com.eneskayiklik.eventverse.data.model.event_detail.Comment
import com.eneskayiklik.eventverse.data.repository.events.EventDetailRepositoryImpl
import com.eneskayiklik.eventverse.util.Resource
import com.eneskayiklik.eventverse.util.UiEvent
import com.eneskayiklik.eventverse.data.state.events.EventDetailState
import com.eneskayiklik.eventverse.data.state.events.RemainingDate
import com.eneskayiklik.eventverse.util.Settings
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

    private var eventId: String = _stateHandle.get<String>("eventId") ?: ""

    init {
        getEventDetail(eventId)
    }

    fun onEvent(event: EventDetailEvent) = viewModelScope.launch {
        when (event) {
            is EventDetailEvent.OnComment -> _state.value = _state.value.copy(
                userComment = _state.value.userComment.copy(
                    comment = event.comment
                )
            )
            is EventDetailEvent.OnRating -> _state.value = _state.value.copy(
                userComment = _state.value.userComment.copy(
                    rating = event.rating
                )
            )
            EventDetailEvent.OnReview -> shareReview()
        }
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
                        event = it.data ?: return@collectLatest
                    )
                    startDateCounter(it.data.startTime)
                }
            }
        }
    }

    private fun startDateCounter(initial: Date) = viewModelScope.launch(Dispatchers.IO) {
        while (true) {
            val remaining = initial.time - System.currentTimeMillis()
            _state.value = _state.value.copy(
                date = if (remaining > 0) remaining.formatDate() else RemainingDate(
                    hour = listOf(0, 0),
                    minute = listOf(0, 0),
                    second = listOf(0, 0)
                )
            )
            delay(1000L)
        }
    }

    private fun shareReview() = viewModelScope.launch(Dispatchers.IO) {
        val currentReview = _state.value.userComment
        val reviews = _state.value.comments.toMutableList()
        val index = reviews.indexOfFirst { it.id == currentReview.id }
        if (((currentReview.comment.isNotEmpty() && currentReview.rating >= 0F)
                    || currentReview.rating > 0F).not()
        ) return@launch
        if (index >= 0) {
            reviews.removeAt(index)
            reviews.add(index, currentReview)
            _event.emit(UiEvent.Toast("Successful!"))
        } else {
            reviews.add(0, currentReview)
            _event.emit(UiEvent.Toast("Successful!"))
        }
        _state.value = _state.value.copy(
            comments = reviews
        )
        eventDetailRepository.shareReview(eventId, currentReview.toDto())
    }

    fun likeEvent(id: String) = viewModelScope.launch(Dispatchers.IO) {
        val event = _state.value.event

        _state.value = _state.value.copy(
            event = _state.value.event.copy(
                isLiked = event.isLiked.not()
            )
        )
        eventDetailRepository.likeEvent(id, event.isLiked.not())
    }

    fun attendEvent(id: String) = viewModelScope.launch(Dispatchers.IO) {
        val event = _state.value.event

        _state.value = _state.value.copy(
            event = _state.value.event.copy(
                isAttended = event.isAttended.not()
            )
        )
        eventDetailRepository.attendeeEvent(id, event.isAttended.not())
    }

    fun getReviews() = viewModelScope.launch(Dispatchers.IO) {
        // Make request if list is empty
        if (_state.value.comments.isNotEmpty()) return@launch
        eventDetailRepository.getReviews(eventId).collectLatest {
            when (it) {
                is Resource.Error -> Unit
                is Resource.Loading -> _state.value = _state.value.copy(
                    isReviewsLoading = true
                )
                is Resource.Success -> {
                    val userId = Settings.currentUser.userId
                    _state.value = _state.value.copy(
                        isReviewsLoading = false,
                        comments = it.data,
                        userComment = it.data.firstOrNull { d -> d.id == userId } ?: Comment(
                            id = Settings.currentUser.userId,
                            user = Settings.currentUser.toPostUser()
                        )
                    )
                }
            }
        }
    }
}
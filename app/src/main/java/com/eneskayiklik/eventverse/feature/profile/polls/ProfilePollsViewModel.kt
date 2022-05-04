package com.eneskayiklik.eventverse.feature.profile.polls

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.eneskayiklik.eventverse.data.model.poll.Poll
import com.eneskayiklik.eventverse.data.repository.profile.ProfilePollsRepositoryImpl
import com.eneskayiklik.eventverse.data.state.poll.PollsState
import com.eneskayiklik.eventverse.util.POLL_PAGE_SIZE
import com.eneskayiklik.eventverse.util.Resource
import com.eneskayiklik.eventverse.util.Settings
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProfilePollsViewModel @Inject constructor(
    private val profilePollsRepository: ProfilePollsRepositoryImpl,
    _stateHandle: SavedStateHandle
) : ViewModel() {

    private val _state = MutableStateFlow(PollsState())
    val state: StateFlow<PollsState> = _state

    private val _getNext: Boolean
        get() = _state.value.isLoading.not() && _state.value.hasNext

    private var userId: String = _stateHandle.get<String>("userId") ?: ""

    init {
        getPolls()
    }

    fun getPolls() = viewModelScope.launch(Dispatchers.IO) {
        profilePollsRepository.getPolls(_getNext, userId).collectLatest {
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
                    polls = _state.value.polls.plus(it.data),
                    hasNext = false
                )
            }
        }
    }

    fun onOptionSelected(pollId: String, optionIndex: Int) = viewModelScope.launch(Dispatchers.IO) {
        val newList = _state.value.polls.map {
            if (it.id == pollId) {
                val newAnswers = it.answers
                newAnswers[Settings.currentUser.userId] = optionIndex
                Poll(
                    id = it.id,
                    title = it.title,
                    options = it.options,
                    answers = newAnswers,
                    createdAt = it.createdAt,
                    fromUser = it.fromUser
                )
            } else it
        }
        _state.value = _state.value.copy(
            polls = newList
        )
        profilePollsRepository.votePoll(pollId, optionIndex)
    }
}
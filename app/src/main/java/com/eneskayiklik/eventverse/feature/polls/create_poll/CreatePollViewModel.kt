package com.eneskayiklik.eventverse.feature.polls.create_poll

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.eneskayiklik.eventverse.util.UiEvent
import com.eneskayiklik.eventverse.data.repository.polls.CreatePollRepositoryImpl
import com.eneskayiklik.eventverse.data.state.poll.CreatePollState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CreatePollViewModel @Inject constructor(
    private val createPollRepository: CreatePollRepositoryImpl
) : ViewModel() {

    private val _state = MutableStateFlow(CreatePollState())
    val state: StateFlow<CreatePollState> = _state

    private val _event = MutableSharedFlow<UiEvent>()
    val event: SharedFlow<UiEvent> = _event

    fun onOptionChanged(text: String, index: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            if (text.count() > 25) return@launch
            val newItems = _state.value.options.toMutableList()
            val lastIndex = newItems.lastIndex

            newItems.removeAt(index)
            newItems.add(index, text)
            if (index == lastIndex && lastIndex < 4) {
                newItems.add("")
            } else if (index == lastIndex - 1 && newItems[lastIndex].isEmpty() && lastIndex > 1 && text.isEmpty()) {
                newItems.removeAt(lastIndex)
            }
            _state.value = _state.value.copy(
                options = newItems
            )
        }
    }

    fun onRemoveOrAdd(isAdd: Boolean, index: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            val newItems = _state.value.options.toMutableList()
            val lastIndex = newItems.lastIndex
            if (isAdd && lastIndex < 4) newItems.add(index, "")
            else if (isAdd.not() && lastIndex > 1) newItems.removeAt(index)
            _state.value = _state.value.copy(
                options = newItems
            )
        }
    }

    fun onTitleChange(title: String) {
        viewModelScope.launch {
            // max length
            if (title.length < 200)
                _state.value = _state.value.copy(
                    title = title
                )
        }
    }

    fun sharePoll() {
        viewModelScope.launch(Dispatchers.IO) {
            val title = _state.value.title
            val options = _state.value.options.filter { it.isNotEmpty() }
            if (options.count() >= 2 && _state.value.isLoading.not() && title.isNotEmpty()) {
                _state.value = _state.value.copy(
                    isLoading = true
                )
                val result = createPollRepository.createPoll(title, options)
                _state.value = _state.value.copy(
                    isLoading = true
                )
                if (result.isSuccess) _event.emit(UiEvent.ClearBackStack)
            }
        }
    }
}
package com.eneskayiklik.eventverse.feature.profile.settings.verify

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.eneskayiklik.eventverse.util.UiEvent
import com.eneskayiklik.eventverse.data.event.profile.VerifyAccountEvent
import com.eneskayiklik.eventverse.data.repository.profile.VerifyAccountRepositoryImpl
import com.eneskayiklik.eventverse.data.state.profile.VerifyAccountState
import com.eneskayiklik.eventverse.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class VerifyAccountViewModel @Inject constructor(
    private val verifyRepository: VerifyAccountRepositoryImpl
) : ViewModel() {

    private val _state = MutableStateFlow(VerifyAccountState())
    val state: StateFlow<VerifyAccountState> = _state

    private val _event = MutableSharedFlow<UiEvent>()
    val event: SharedFlow<UiEvent> = _event

    fun onEvent(event: VerifyAccountEvent) {
        when (event) {
            is VerifyAccountEvent.OnVerifyCode -> _state.value = _state.value.copy(
                verifyCode = _state.value.verifyCode.copy(
                    text = event.text, error = ""
                )
            )
            VerifyAccountEvent.OnVerifyAccount -> verifyAccount()
        }
    }

    private fun verifyAccount() = viewModelScope.launch(Dispatchers.IO) {
        if (_state.value.canVerify.not()) return@launch
        val code = _state.value.verifyCode.text
        verifyRepository.verifyAccount(code).collectLatest {
            when (it) {
                is Resource.Error -> _state.value = _state.value.copy(
                    isLoading = false,
                    verifyCode = _state.value.verifyCode.copy(
                        error = it.errorMessage ?: ""
                    )
                )
                is Resource.Loading -> _state.value = _state.value.copy(
                    isLoading = true,
                )
                is Resource.Success -> {
                    _event.emit(UiEvent.ClearBackStack)
                }
            }
        }
    }
}
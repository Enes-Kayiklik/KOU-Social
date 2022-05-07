package com.eneskayiklik.eventverse.feature.auth.signup.verify_email

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.eneskayiklik.eventverse.util.Screen
import com.eneskayiklik.eventverse.util.UiEvent
import com.eneskayiklik.eventverse.data.event.auth.VerifyEmailEvent
import com.eneskayiklik.eventverse.data.repository.auth.VerifyEmailRepositoryImpl
import com.eneskayiklik.eventverse.data.state.auth.VerifyEmailState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class VerifyEmailViewModel @Inject constructor(
    private val verifyRepository: VerifyEmailRepositoryImpl,
    stateHandle: SavedStateHandle
) : ViewModel() {
    private val _state = MutableStateFlow(VerifyEmailState())
    val state: StateFlow<VerifyEmailState> = _state

    private val _event = MutableSharedFlow<UiEvent>()
    val event: SharedFlow<UiEvent> = _event

    private var _isTimeCounting: Boolean = false

    private val showInitialTime = stateHandle.get<Boolean>("initialTime") ?: false

    init {
        if (showInitialTime) startTiming()
    }

    fun onEvent(
        data: VerifyEmailEvent
    ) {
        when (data) {
            VerifyEmailEvent.OnContinue -> checkEmailVerified()
            VerifyEmailEvent.OnResend -> resendMail()
        }
    }

    private fun resendMail() {
        viewModelScope.launch(Dispatchers.IO) {
            if (_isTimeCounting.not() && verifyRepository.resendMail()) {
                _event.emit(UiEvent.Toast("Resend Successful!"))
                startTiming()
            } else {
                _event.emit(UiEvent.Toast("You can not resend!"))
            }
        }
    }

    private fun checkEmailVerified() {
        viewModelScope.launch(Dispatchers.IO) {
            if (verifyRepository.checkEmailVerified()) {
                _event.emit(UiEvent.Navigate(Screen.Home.route))
            } else {
                _event.emit(UiEvent.Toast("Not Verified"))
            }
        }
    }

    private fun startTiming() = viewModelScope.launch(Dispatchers.IO) {
        if (_isTimeCounting) return@launch
        _isTimeCounting = true
        var time = 180
        while (time >= 0) {
            _state.value = _state.value.copy(
                resendSecond = time
            )
            time -= 1
            delay(1000)
        }
        _isTimeCounting = false
    }
}
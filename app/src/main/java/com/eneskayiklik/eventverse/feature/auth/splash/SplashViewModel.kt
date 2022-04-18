package com.eneskayiklik.eventverse.feature.auth.splash

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.eneskayiklik.eventverse.core.model.ErrorState
import com.eneskayiklik.eventverse.util.Screen
import com.eneskayiklik.eventverse.util.UiEvent
import com.eneskayiklik.eventverse.data.event.auth.SplashEvent
import com.eneskayiklik.eventverse.data.repository.auth.SplashRepositoryImpl
import com.eneskayiklik.eventverse.data.state.auth.SplashState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SplashViewModel @Inject constructor(
    private val splashRepository: SplashRepositoryImpl,
) : ViewModel() {
    private val _state = MutableStateFlow(SplashState())
    val state: StateFlow<SplashState> = _state

    private val _uiEvent = MutableSharedFlow<UiEvent>()
    val uiEvent: SharedFlow<UiEvent> = _uiEvent

    private var _canResend: Boolean = true

    fun initUser() {
        viewModelScope.launch(Dispatchers.IO) {
            when (val result = splashRepository.checkUser()) {
                is SplashEvent.OnNavigate -> {
                    delay(100L)
                    _uiEvent.emit(UiEvent.Navigate(result.route))
                }
                SplashEvent.ShowVerifyPopup -> showVerificationDialog()
            }
        }
    }

    private fun showVerificationDialog() {
        viewModelScope.launch(Dispatchers.IO) {
            _state.value = _state.value.copy(
                dialogState = ErrorState(
                    title = "Verify Account",
                    subTitle = "Please click on the link that has just been sent to your email account to verify your email.",
                    firstButtonText = "Resend",
                    secondButtonText = "Continue",
                    firstButtonClick = { resendMail() },
                    secondButtonClick = { checkEmailVerified() }
                )
            )
        }
    }

    private fun resendMail() {
        viewModelScope.launch(Dispatchers.IO) {
            if (_canResend && splashRepository.resendMail()) {
                _uiEvent.emit(UiEvent.Toast("Resend Successful!"))
            } else {
                _uiEvent.emit(UiEvent.Toast("You can not resend!"))
            }
        }
    }

    private fun checkEmailVerified() {
        viewModelScope.launch(Dispatchers.IO) {
            if (splashRepository.checkEmailVerified()) {
                _uiEvent.emit(UiEvent.Navigate(Screen.Home.route))
            } else {
                _uiEvent.emit(UiEvent.Toast("Not Verified"))
            }
        }
    }
}
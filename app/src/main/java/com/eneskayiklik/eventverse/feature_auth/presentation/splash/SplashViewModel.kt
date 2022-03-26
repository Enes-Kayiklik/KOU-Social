package com.eneskayiklik.eventverse.feature_auth.presentation.splash

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.eneskayiklik.eventverse.core.model.ErrorState
import com.eneskayiklik.eventverse.core.util.Screen
import com.eneskayiklik.eventverse.core.util.UiEvent
import com.eneskayiklik.eventverse.feature_auth.data.event.SplashEvent
import com.eneskayiklik.eventverse.feature_auth.data.repository.SplashRepositoryImpl
import com.eneskayiklik.eventverse.feature_auth.data.repository.StreamRepositoryImpl
import com.eneskayiklik.eventverse.feature_auth.data.state.SplashState
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
    private val streamRepository: StreamRepositoryImpl,
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
                    if (result.route == Screen.Explore.route) streamRepository.connectUser()
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
                _uiEvent.emit(UiEvent.Navigate(Screen.Explore.route)).also {
                    streamRepository.connectUser()
                }
            } else {
                _uiEvent.emit(UiEvent.Toast("Not Verified"))
            }
        }
    }
}
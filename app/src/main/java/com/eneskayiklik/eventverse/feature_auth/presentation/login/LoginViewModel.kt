package com.eneskayiklik.eventverse.feature_auth.presentation.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.eneskayiklik.eventverse.core.model.ErrorState
import com.eneskayiklik.eventverse.core.util.Screen
import com.eneskayiklik.eventverse.core.util.UiEvent
import com.eneskayiklik.eventverse.core.util.extension.isValidEmail
import com.eneskayiklik.eventverse.core.util.extension.isValidPassword
import com.eneskayiklik.eventverse.feature_auth.data.event.LoginEvent
import com.eneskayiklik.eventverse.feature_auth.data.repository.LoginRepositoryImpl
import com.eneskayiklik.eventverse.feature_auth.data.state.LoginState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val loginRepository: LoginRepositoryImpl,
) : ViewModel() {
    private val _state = MutableStateFlow(LoginState())
    val state: StateFlow<LoginState> = _state

    private val _event = MutableSharedFlow<UiEvent>()
    val event: SharedFlow<UiEvent> = _event

    private var _canResend: Boolean = true

    fun onLoginState(
        data: LoginEvent
    ) {
        when (data) {
            is LoginEvent.OnEmail -> {
                _state.value = _state.value.copy(
                    email = _state.value.email.copy(
                        text = data.email, error = ""
                    )
                )
            }
            is LoginEvent.OnPassword -> {
                _state.value = _state.value.copy(
                    password = _state.value.password.copy(
                        text = data.password, error = ""
                    )
                )
            }
            LoginEvent.OnTogglePassword -> {
                _state.value =
                    _state.value.copy(isPasswordVisible = _state.value.isPasswordVisible.not())
            }
            LoginEvent.OnLogin -> loginWithEmailAndPassword()
            else -> Unit
        }
    }

    private fun loginWithEmailAndPassword() {
        viewModelScope.launch {
            val email = _state.value.email.text
            val password = _state.value.password.text
            if (email.isValidEmail().not()) {
                _state.value = _state.value.copy(
                    email = _state.value.email.copy(error = "Email is not valid"),
                )
                return@launch
            }
            if (password.isValidPassword().not()) {
                _state.value = _state.value.copy(
                    password = _state.value.password.copy(error = "Password is not valid"),
                )
                return@launch
            }
            if (_state.value.isLoading) return@launch
            _state.value = _state.value.copy(isLoading = true)

            when (val event = loginRepository.loginWithEmail(email, password)) {
                is LoginEvent.OnNavigate -> _event.emit(UiEvent.Navigate(event.route))
                LoginEvent.ShowErrorPopup -> showErrorDialog()
                LoginEvent.ShowVerifyPopup -> showVerificationDialog()
                LoginEvent.PasswordError -> _state.value = _state.value.copy(
                    isLoading = false,
                    password = _state.value.password.copy(error = "Wrong password"),
                )
                LoginEvent.UserError -> _state.value = _state.value.copy(
                    isLoading = false,
                    email = _state.value.email.copy(error = "There is no user using this mail"),
                )
                else -> Unit
            }
        }
    }

    private fun showVerificationDialog() {
        viewModelScope.launch(Dispatchers.IO) {
            _state.value = _state.value.copy(
                isLoading = false,
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

    private fun showErrorDialog() {
        viewModelScope.launch(Dispatchers.IO) {
            _state.value = _state.value.copy(
                isLoading = false,
                dialogState = ErrorState(
                    title = "Error Occured",
                    subTitle = "An error occurred. Please try again later.",
                    secondButtonText = "Ok",
                    secondButtonClick = { _state.value = _state.value.copy(dialogState = null) }
                )
            )
        }
    }

    private fun resendMail() {
        viewModelScope.launch(Dispatchers.IO) {
            if (_canResend && loginRepository.resendMail()) {
                _event.emit(UiEvent.Toast("Resend Successful!"))
            } else {
                _event.emit(UiEvent.Toast("You can not resend!"))
            }
        }
    }

    private fun checkEmailVerified() {
        viewModelScope.launch(Dispatchers.IO) {
            if (loginRepository.checkEmailVerified()) {
                _event.emit(UiEvent.Navigate(Screen.Home.route))
            } else {
                _event.emit(UiEvent.Toast("Not Verified"))
            }
        }
    }
}
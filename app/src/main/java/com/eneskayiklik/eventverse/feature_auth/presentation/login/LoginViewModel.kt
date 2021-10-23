package com.eneskayiklik.eventverse.feature_auth.presentation.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.eneskayiklik.eventverse.core.util.Screen
import com.eneskayiklik.eventverse.core.util.TextFieldState
import com.eneskayiklik.eventverse.core.util.UiEvent
import com.eneskayiklik.eventverse.feature_auth.domain.use_case.LoginUseCase
import com.eneskayiklik.eventverse.feature_auth.presentation.login.util.LoginState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val loginUseCase: LoginUseCase
) : ViewModel() {
    private val _emailState = MutableStateFlow(TextFieldState())
    val emailState: StateFlow<TextFieldState> = _emailState

    private val _passwordState = MutableStateFlow(TextFieldState())
    val passwordState: StateFlow<TextFieldState> = _passwordState

    private val _googleButtonState = MutableStateFlow(false)
    val googleButtonState: StateFlow<Boolean> = _googleButtonState

    private val _uiState = MutableSharedFlow<UiEvent>()
    val uiState: SharedFlow<UiEvent> = _uiState

    fun onLoginState(
        data: LoginState
    ) {
        when (data) {
            is LoginState.OnEmail -> {
                _emailState.value = _emailState.value.copy(text = data.email, error = "")
            }
            is LoginState.OnPassword -> {
                _passwordState.value = _passwordState.value.copy(text = data.password, error = "")
            }
            LoginState.OnLogin -> {
                loginWithEmailAndPassword()
            }
            LoginState.OnGoogle -> {
                _googleButtonState.value = _googleButtonState.value.not()
                loginWithGoogle()
            }
            LoginState.OnRegister -> {
                viewModelScope.launch {
                    _uiState.emit(UiEvent.Navigate(Screen.Signup.route))
                }
            }
            LoginState.OnTogglePassword -> {
                _passwordState.value =
                    _passwordState.value.copy(isPasswordShowing = _passwordState.value.isPasswordShowing.not())
            }
        }
    }

    private fun loginWithEmailAndPassword() {
        viewModelScope.launch {
            val email = emailState.value
            val password = passwordState.value
            val result = loginUseCase.loginWithEmail(email.text, password.text)
            _emailState.value = _emailState.value.copy(error = result.emailError)
            _passwordState.value = _passwordState.value.copy(error = result.passwordError)
            if (result.isSuccess) {
                _uiState.emit(UiEvent.Navigate(Screen.Timeline.route))
            }
        }
    }

    private fun loginWithGoogle() {
        viewModelScope.launch {
            // do something
        }
    }
}
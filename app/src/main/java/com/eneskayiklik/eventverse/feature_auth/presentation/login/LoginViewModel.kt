package com.eneskayiklik.eventverse.feature_auth.presentation.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.eneskayiklik.eventverse.core.util.Screen
import com.eneskayiklik.eventverse.core.util.TextFieldState
import com.eneskayiklik.eventverse.core.util.UiEvent
import com.eneskayiklik.eventverse.core.util.extension.isValidEmail
import com.eneskayiklik.eventverse.core.util.extension.isValidPassword
import com.eneskayiklik.eventverse.feature_auth.domain.use_case.LoginUseCase
import com.eneskayiklik.eventverse.feature_auth.presentation.login.util.LoginState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
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

    private val _loginButtonState = MutableStateFlow(false)
    val loginButtonState: StateFlow<Boolean> = _loginButtonState

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
                viewModelScope.launch {
                    _uiState.emit(UiEvent.Navigate(Screen.Explore.route))
                }
                // loginWithEmailAndPassword()
            }
            LoginState.OnGoogle -> {
                _googleButtonState.value = _googleButtonState.value.not()
                loginWithGoogle()
            }
            LoginState.OnNavigateRegister -> {
                viewModelScope.launch {
                    _uiState.emit(UiEvent.Navigate(Screen.Signup.route))
                }
            }
            LoginState.OnTogglePassword -> {
                _passwordState.value =
                    _passwordState.value.copy(isPasswordShowing = _passwordState.value.isPasswordShowing.not())
            }
            is LoginState.OnFullname -> {
            }
            LoginState.OnRegister -> {
            }
        }
    }

    private fun loginWithEmailAndPassword() {
        viewModelScope.launch {
            val email = emailState.value.text
            val password = passwordState.value.text
            if (email.isValidEmail() && password.isValidPassword())
                _loginButtonState.value = _loginButtonState.value.not()

            val result = loginUseCase.loginWithEmail(email, password)
            _emailState.value = _emailState.value.copy(error = result.emailError)
            _passwordState.value = _passwordState.value.copy(error = result.passwordError)
            if (result.isSuccess) {
                _loginButtonState.value = _loginButtonState.value.not()
                _uiState.emit(UiEvent.Navigate(Screen.Explore.route))
            }
        }
    }

    private fun loginWithGoogle() {
        viewModelScope.launch {
            delay(3000L)
            _googleButtonState.value = _googleButtonState.value.not()
        }
    }
}
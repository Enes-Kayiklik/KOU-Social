package com.eneskayiklik.eventverse.feature_auth.presentation.signup

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.eneskayiklik.eventverse.core.util.TextFieldState
import com.eneskayiklik.eventverse.core.util.UiEvent
import com.eneskayiklik.eventverse.core.util.extension.isValidEmail
import com.eneskayiklik.eventverse.core.util.extension.isValidPassword
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
class SignupViewModel @Inject constructor(
    private val loginUseCase: LoginUseCase
) : ViewModel() {
    private val _emailState = MutableStateFlow(TextFieldState())
    val emailState: StateFlow<TextFieldState> = _emailState

    private val _fullnameState = MutableStateFlow(TextFieldState())
    val fullnameState: StateFlow<TextFieldState> = _fullnameState

    private val _passwordState = MutableStateFlow(TextFieldState())
    val passwordState: StateFlow<TextFieldState> = _passwordState

    private val _signupButtonState = MutableStateFlow(false)
    val signupButtonState: StateFlow<Boolean> = _signupButtonState

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
            is LoginState.OnFullname -> {
                _fullnameState.value = _fullnameState.value.copy(text = data.fullname, error = "")
            }
            LoginState.OnLogin -> {
            }
            LoginState.OnGoogle -> {
            }
            LoginState.OnNavigateRegister -> {
            }
            LoginState.OnTogglePassword -> {
                _passwordState.value =
                    _passwordState.value.copy(isPasswordShowing = _passwordState.value.isPasswordShowing.not())
            }
            LoginState.OnRegister -> {
                registerWithEmailAndPassword()
            }
        }
    }

    private fun registerWithEmailAndPassword() {
        viewModelScope.launch {
            val email = emailState.value.text
            val password = passwordState.value.text
            if (email.isValidEmail() && password.isValidPassword())
                _signupButtonState.value = _signupButtonState.value.not()

            val result = loginUseCase.loginWithEmail(email, password)
            _emailState.value = _emailState.value.copy(error = result.emailError)
            _passwordState.value = _passwordState.value.copy(error = result.passwordError)
            if (result.isSuccess) {
                _signupButtonState.value = _signupButtonState.value.not()
                _uiState.emit(UiEvent.CleatBackStack)
            }
        }
    }
}
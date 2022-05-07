package com.eneskayiklik.eventverse.feature.auth.signup

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.eneskayiklik.eventverse.util.Screen
import com.eneskayiklik.eventverse.util.UiEvent
import com.eneskayiklik.eventverse.util.extension.isValidEmail
import com.eneskayiklik.eventverse.util.extension.isValidFullName
import com.eneskayiklik.eventverse.util.extension.isValidPassword
import com.eneskayiklik.eventverse.data.event.auth.SignupEvent
import com.eneskayiklik.eventverse.data.repository.auth.SignupRepositoryImpl
import com.eneskayiklik.eventverse.data.state.auth.SignupState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SignupViewModel @Inject constructor(
    private val signupRepository: SignupRepositoryImpl,
) : ViewModel() {
    private val _state = MutableStateFlow(SignupState())
    val state: StateFlow<SignupState> = _state

    private val _event = MutableSharedFlow<UiEvent>()
    val event: SharedFlow<UiEvent> = _event

    fun onSignupEvent(
        data: SignupEvent
    ) {
        when (data) {
            is SignupEvent.OnEmail -> {
                _state.value = _state.value.copy(
                    email = _state.value.email.copy(
                        text = data.email, error = ""
                    )
                )
            }
            is SignupEvent.OnPassword -> {
                _state.value = _state.value.copy(
                    password = _state.value.password.copy(
                        text = data.password, error = ""
                    )
                )
            }
            is SignupEvent.OnFullName -> {
                _state.value = _state.value.copy(
                    fullName = _state.value.fullName.copy(
                        text = data.fullName, error = ""
                    )
                )
            }
            SignupEvent.OnTogglePassword -> {
                _state.value =
                    _state.value.copy(isPasswordVisible = _state.value.isPasswordVisible.not())
            }
            SignupEvent.OnRegister -> {
                registerWithEmailAndPassword()
            }
            SignupEvent.OnLogin -> {
                viewModelScope.launch {
                    _event.emit(UiEvent.ClearBackStack)
                }
            }
            SignupEvent.ShowFacultyPopup -> {
                _state.value = _state.value.copy(
                    isFacultyPopupActive = _state.value.isFacultyPopupActive.not()
                )
            }
        }
    }

    private fun registerWithEmailAndPassword() {
        viewModelScope.launch(Dispatchers.IO) {
            val email = _state.value.email.text
            val password = _state.value.password.text
            val fullName = _state.value.fullName.text

            if (fullName.isValidFullName().not()) {
                _state.value = _state.value.copy(
                    fullName = _state.value.fullName.copy(error = "Full name is not valid."),
                )
                return@launch
            }
            if (email.isValidEmail().not()) {
                _state.value = _state.value.copy(
                    email = _state.value.email.copy(error = "Email is not valid."),
                )
                return@launch
            }
            if (password.isValidPassword().not()) {
                _state.value = _state.value.copy(
                    password = _state.value.password.copy(error = "Password is not valid."),
                )
                return@launch
            }
            if (_state.value.isLoading) return@launch
            _state.value = _state.value.copy(
                isLoading = true
            )

            val result = signupRepository.signupWithEmail(email, password, fullName)
            if (result.isSuccess.not()) {
                _state.value = _state.value.copy(
                    email = _state.value.email.copy(error = result.emailError),
                    password = _state.value.password.copy(error = result.passwordError),
                    isLoading = false
                )
            } else {
                _event.emit(UiEvent.Navigate(Screen.VerifyEmail.route(true)))
            }
        }
    }
}
package com.eneskayiklik.eventverse.feature_auth.presentation.signup

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.eneskayiklik.eventverse.core.util.Resource
import com.eneskayiklik.eventverse.core.util.UiEvent
import com.eneskayiklik.eventverse.feature_auth.data.event.SignupEvent
import com.eneskayiklik.eventverse.feature_auth.data.repository.SignupRepositoryImpl
import com.eneskayiklik.eventverse.feature_auth.data.state.SignupState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SignupViewModel @Inject constructor(
    private val signupRepository: SignupRepositoryImpl
) : ViewModel() {
    private val _state = MutableStateFlow(SignupState())
    val state: StateFlow<SignupState> = _state

    private val _event = MutableSharedFlow<UiEvent>()
    val event: SharedFlow<UiEvent> = _event

    init {
        getFaculties()
    }

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
            is SignupEvent.SelectDepartment -> {
                if (data.department.departmentName.isNotEmpty()) {
                    _state.value = _state.value.copy(
                        selectedDepartment = data.department,
                        isFacultyPopupActive = false
                    )
                } else {
                    _state.value = _state.value.copy(
                        isFacultyPopupActive = false
                    )
                }
            }
            else -> Unit
        }
    }

    private fun getFaculties() {
        viewModelScope.launch(Dispatchers.IO) {
            signupRepository.getFaculties().collectLatest {
                when (it) {
                    is Resource.Error -> _state.value = _state.value.copy(
                        isFacultiesLoading = false
                    )
                    is Resource.Loading -> _state.value = _state.value.copy(
                        isFacultiesLoading = true
                    )
                    is Resource.Success -> _state.value = _state.value.copy(
                        faculties = it.data,
                        isFacultiesLoading = false
                    )
                }
            }
        }
    }

    private fun registerWithEmailAndPassword() {
        viewModelScope.launch {
            /*val email = emailState.value.text
            val password = passwordState.value.text
            if (email.isValidEmail() && password.isValidPassword())
                _signupButtonState.value = _signupButtonState.value.not()

            val result = loginUseCase.loginWithEmail(email, password)
            _emailState.value = _emailState.value.copy(error = result.emailError)
            _passwordState.value = _passwordState.value.copy(error = result.passwordError)
            if (result.isSuccess) {
                _signupButtonState.value = _signupButtonState.value.not()
                _uiState.emit(UiEvent.ClearBackStack)
            }*/
        }
    }

    private fun loginWithGoogle() {
        viewModelScope.launch {

        }
    }
}
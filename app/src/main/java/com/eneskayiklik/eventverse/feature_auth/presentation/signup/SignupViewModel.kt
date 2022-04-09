package com.eneskayiklik.eventverse.feature_auth.presentation.signup

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.eneskayiklik.eventverse.core.model.ErrorState
import com.eneskayiklik.eventverse.core.util.Resource
import com.eneskayiklik.eventverse.core.util.Screen
import com.eneskayiklik.eventverse.core.util.UiEvent
import com.eneskayiklik.eventverse.core.util.extension.isValidEmail
import com.eneskayiklik.eventverse.core.util.extension.isValidFullName
import com.eneskayiklik.eventverse.core.util.extension.isValidPassword
import com.eneskayiklik.eventverse.feature_auth.data.event.SignupEvent
import com.eneskayiklik.eventverse.feature_auth.data.repository.SignupRepositoryImpl
import com.eneskayiklik.eventverse.feature_auth.data.repository.StreamRepositoryImpl
import com.eneskayiklik.eventverse.feature_auth.data.state.SignupState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SignupViewModel @Inject constructor(
    private val signupRepository: SignupRepositoryImpl,
    private val streamRepository: StreamRepositoryImpl
) : ViewModel() {
    private val _state = MutableStateFlow(SignupState())
    val state: StateFlow<SignupState> = _state

    private val _event = MutableSharedFlow<UiEvent>()
    val event: SharedFlow<UiEvent> = _event

    private var _canResend: Boolean = true

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
                showVerificationDialog()
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

    private fun resendMail() {
        viewModelScope.launch(Dispatchers.IO) {
            if (_canResend && signupRepository.resendMail()) {
                _event.emit(UiEvent.Toast("Resend Successful!"))
            } else {
                _event.emit(UiEvent.Toast("You can not resend!"))
            }
        }
    }

    private fun checkEmailVerified() {
        viewModelScope.launch(Dispatchers.IO) {
            if (signupRepository.checkEmailVerified()) {
                _event.emit(UiEvent.Navigate(Screen.Home.route)).also {
                    streamRepository.connectUser()
                }
            } else {
                _event.emit(UiEvent.Toast("Not Verified"))
            }
        }
    }

    private fun loginWithGoogle() {
        viewModelScope.launch {

        }
    }
}
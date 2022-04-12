package com.eneskayiklik.eventverse.feature_settings.presentation.delete_account

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.eneskayiklik.eventverse.core.util.Resource
import com.eneskayiklik.eventverse.core.util.UiEvent
import com.eneskayiklik.eventverse.core.util.extension.isValidEmail
import com.eneskayiklik.eventverse.core.util.extension.isValidPassword
import com.eneskayiklik.eventverse.feature_settings.data.event.UpdatePasswordEvent
import com.eneskayiklik.eventverse.feature_settings.data.repository.DeleteAccountRepositoryImpl
import com.eneskayiklik.eventverse.feature_settings.data.state.UpdatePasswordState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DeleteAccountViewModel @Inject constructor(
    private val deleteAccountRepository: DeleteAccountRepositoryImpl
) : ViewModel() {

    private val _state = MutableStateFlow(UpdatePasswordState())
    val state: StateFlow<UpdatePasswordState> = _state

    private val _event = MutableSharedFlow<UiEvent>()
    val event: SharedFlow<UiEvent> = _event

    fun onEvent(event: UpdatePasswordEvent) {
        when (event) {
            is UpdatePasswordEvent.OnEmail -> _state.value = _state.value.copy(
                email = _state.value.email.copy(
                    text = event.email, error = ""
                )
            )
            is UpdatePasswordEvent.OnNewPassword -> _state.value = _state.value.copy(
                newPassword = _state.value.newPassword.copy(
                    text = event.password, error = ""
                )
            )
            is UpdatePasswordEvent.OnPassword -> _state.value = _state.value.copy(
                password = _state.value.password.copy(
                    text = event.password, error = ""
                )
            )
            UpdatePasswordEvent.OnToggleNewPassword -> _state.value = _state.value.copy(
                isNewPasswordVisible = _state.value.isNewPasswordVisible.not()
            )
            UpdatePasswordEvent.OnTogglePassword -> _state.value = _state.value.copy(
                isPasswordVisible = _state.value.isPasswordVisible.not()
            )
            UpdatePasswordEvent.OnReset -> deleteAccount()
        }
    }

    private fun deleteAccount() {
        viewModelScope.launch(Dispatchers.IO) {
            val email = _state.value.email.text
            val password = _state.value.password.text
            val newPassword = _state.value.newPassword.text

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
            if (newPassword.isValidPassword().not()) {
                _state.value = _state.value.copy(
                    newPassword = _state.value.newPassword.copy(error = "Password is not valid."),
                )
                return@launch
            }

            if (_state.value.isLoading) return@launch

            // TODO("Popup çıkar tamam derse sil")
            deleteAccountRepository.deleteAccount(email, password).collectLatest {
                when (it) {
                    is Resource.Error -> _state.value = _state.value.copy(
                        email = _state.value.email.copy(
                            error = it.data?.emailError ?: ""
                        ),
                        password = _state.value.password.copy(
                            error = it.data?.passwordError ?: ""
                        ), isLoading = false
                    )
                    is Resource.Loading -> _state.value = _state.value.copy(
                        isLoading = true
                    )
                    is Resource.Success -> _event.emit(UiEvent.RestartApp)
                }
            }
        }
    }
}
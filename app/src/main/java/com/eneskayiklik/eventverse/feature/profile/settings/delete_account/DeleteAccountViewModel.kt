package com.eneskayiklik.eventverse.feature.profile.settings.delete_account

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.eneskayiklik.eventverse.core.data.model.ErrorState
import com.eneskayiklik.eventverse.util.Resource
import com.eneskayiklik.eventverse.util.Settings
import com.eneskayiklik.eventverse.util.UiEvent
import com.eneskayiklik.eventverse.util.extension.isValidPassword
import com.eneskayiklik.eventverse.data.event.profile.UpdatePasswordEvent
import com.eneskayiklik.eventverse.data.repository.profile.DeleteAccountRepositoryImpl
import com.eneskayiklik.eventverse.data.state.profile.UpdatePasswordState
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

            if (email != Settings.currentUser.email) {
                _state.value = _state.value.copy(
                    email = _state.value.email.copy(error = "Wrong Email"),
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

            if (newPassword != password) {
                _state.value = _state.value.copy(
                    newPassword = _state.value.newPassword.copy(error = "Password's are not matching"),
                )
                return@launch
            }

            if (_state.value.isLoading) return@launch

            // Show popup
            _state.value = _state.value.copy(
                isLoading = false,
                errorDialogState = ErrorState(
                    title = "Delete Account Permanently",
                    subTitle = "Do you really want to delete your KOU Social account permanently ?",
                    firstButtonText = "No",
                    secondButtonText = "Yes",
                    firstButtonClick = {
                        _state.value = _state.value.copy(
                            errorDialogState = null
                        )
                    },
                    secondButtonClick = { deleteAccountPermanently(email, password) }
                )
            )
        }
    }

    private fun deleteAccountPermanently(email: String, password: String) {
        viewModelScope.launch(Dispatchers.IO) {
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
                        isLoading = true, errorDialogState = null
                    )
                    is Resource.Success -> _event.emit(UiEvent.RestartApp)
                }
            }
        }
    }
}
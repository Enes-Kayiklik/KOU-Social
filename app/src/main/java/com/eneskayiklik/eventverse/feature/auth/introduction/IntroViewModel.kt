package com.eneskayiklik.eventverse.feature.auth.introduction

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.eneskayiklik.eventverse.core.data.model.ErrorState
import com.eneskayiklik.eventverse.util.Resource
import com.eneskayiklik.eventverse.util.Screen
import com.eneskayiklik.eventverse.util.UiEvent
import com.eneskayiklik.eventverse.data.event.auth.IntroEvent
import com.eneskayiklik.eventverse.data.state.auth.IntroState
import com.eneskayiklik.eventverse.data.repository.auth.IntroRepositoryImpl
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class IntroViewModel @Inject constructor(
    private val introRepository: IntroRepositoryImpl
) : ViewModel() {
    private val _state = MutableStateFlow(IntroState())
    val state: StateFlow<IntroState> = _state

    private val _uiEvent = MutableSharedFlow<UiEvent>()
    val uiEvent: SharedFlow<UiEvent> = _uiEvent

    fun onEvent(
        data: IntroEvent
    ) {
        viewModelScope.launch {
            when (data) {
                IntroEvent.OnLogin -> _uiEvent.emit(UiEvent.Navigate(Screen.Login.route))
                IntroEvent.OnRegister -> _uiEvent.emit(UiEvent.Navigate(Screen.Signup.route))
            }
        }
    }

    fun loginWithGoogle(account: GoogleSignInAccount) {
        viewModelScope.launch {
            introRepository.signupWithGoogle(account).collectLatest {
                when (it) {
                    is Resource.Error -> showErrorDialog()
                    is Resource.Loading -> updateGoogleState(true)
                    is Resource.Success -> {
                        updateGoogleState(false)
                        _uiEvent.emit(UiEvent.Navigate(Screen.Home.route))
                    }
                }
            }
        }
    }

    private fun showErrorDialog() {
        viewModelScope.launch(Dispatchers.IO) {
            _state.value = _state.value.copy(
                isGoogleLoading = false,
                errorDialogState = ErrorState(
                    title = "Error Occurred",
                    subTitle = "An unknown error occurred",
                    firstButtonText = "OK",
                    firstButtonClick = { _state.value = _state.value.copy(
                        errorDialogState = null
                    ) },
                )
            )
        }
    }

    fun updateGoogleState(value: Boolean = true) {
        _state.value = _state.value.copy(
            isGoogleLoading = value
        )
    }
}
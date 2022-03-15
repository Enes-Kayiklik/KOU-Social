package com.eneskayiklik.eventverse.feature_auth.presentation.introduction

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.eneskayiklik.eventverse.core.util.Screen
import com.eneskayiklik.eventverse.core.util.UiEvent
import com.eneskayiklik.eventverse.feature_auth.data.event.IntroEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class IntroViewModel @Inject constructor() : ViewModel() {
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
}
package com.eneskayiklik.eventverse.feature_explore.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.eneskayiklik.eventverse.feature_explore.domain.model.ExploreEventModel
import com.eneskayiklik.eventverse.feature_explore.domain.use_case.ExploreUseCase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ExploreViewModel @Inject constructor(
    private val exploreUseCase: ExploreUseCase
) : ViewModel(

) {
    private val _upcomingEvents = MutableStateFlow<List<ExploreEventModel>>(emptyList())
    val upcomingEvents: StateFlow<List<ExploreEventModel>> = _upcomingEvents

    init {
        getUpcomingEvents()
    }

    private fun getUpcomingEvents() {
        viewModelScope.launch {
            _upcomingEvents.value = exploreUseCase.getUpcomingEvents()
        }
    }

    fun logOut() {
        Firebase.auth.signOut()
    }
}
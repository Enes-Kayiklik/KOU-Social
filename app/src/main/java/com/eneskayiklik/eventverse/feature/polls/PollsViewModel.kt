package com.eneskayiklik.eventverse.feature.polls

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.eneskayiklik.eventverse.data.repository.polls.PollsRepositoryImpl
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PollsViewModel @Inject constructor(
    private val pollsRepository: PollsRepositoryImpl
): ViewModel() {

    init {
        viewModelScope.launch {
            Log.e("TAG", "${pollsRepository.getPolls().first().percentages}: ", )
        }
    }
}
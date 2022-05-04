package com.eneskayiklik.eventverse.feature.profile.posts

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.eneskayiklik.eventverse.data.repository.profile.ProfilePostsRepositoryImpl
import com.eneskayiklik.eventverse.data.state.explore.PostsState
import com.eneskayiklik.eventverse.util.POST_PAGE_SIZE
import com.eneskayiklik.eventverse.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProfilePostsViewModel @Inject constructor(
    private val profilePostsRepository: ProfilePostsRepositoryImpl,
    _stateHandle: SavedStateHandle
) : ViewModel() {

    private val _state = MutableStateFlow(PostsState())
    val state: StateFlow<PostsState> = _state

    private val _getNext: Boolean
        get() = _state.value.isLoading.not() && _state.value.hasNext

    private var userId: String = _stateHandle.get<String>("userId") ?: ""

    init {
        getPolls()
    }

    fun getPolls() = viewModelScope.launch(Dispatchers.IO) {
        profilePostsRepository.getPosts(_getNext, userId).collectLatest {
            when (it) {
                is Resource.Error -> _state.value = _state.value.copy(
                    isLoading = false,
                    hasNext = false
                )
                is Resource.Loading -> _state.value = _state.value.copy(
                    isLoading = true
                )
                is Resource.Success -> _state.value = _state.value.copy(
                    isLoading = false,
                    posts = _state.value.posts.plus(it.data),
                    hasNext = false
                )
            }
        }
    }
}
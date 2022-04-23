package com.eneskayiklik.eventverse.feature.explore

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.eneskayiklik.eventverse.data.model.share.Post
import com.eneskayiklik.eventverse.data.repository.explore.ExploreRepositoryImpl
import com.eneskayiklik.eventverse.data.state.explore.PostsState
import com.eneskayiklik.eventverse.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ExploreViewModel @Inject constructor(
    private val exploreRepository: ExploreRepositoryImpl
) : ViewModel() {
    private val _state = MutableStateFlow(PostsState())
    val state: StateFlow<PostsState> = _state

    private val _getNext: Boolean
        get() = _state.value.isLoading.not() && _state.value.hasNext

    private val _canRefresh: Boolean
        get() = _state.value.isLoading.not()

    init {
        getPolls()
    }

    fun getPolls() = viewModelScope.launch(Dispatchers.IO) {
        exploreRepository.getPosts(_getNext).collectLatest {
            when (it) {
                is Resource.Error -> _state.value = _state.value.copy(
                    isLoading = false
                )
                is Resource.Loading -> _state.value = _state.value.copy(
                    isLoading = true
                )
                is Resource.Success -> _state.value = _state.value.copy(
                    isLoading = false,
                    posts = _state.value.posts.plus(it.data),
                    hasNext = it.data.count() > 3
                )
            }
        }
    }

    fun refreshPosts() = viewModelScope.launch(Dispatchers.IO) {
        exploreRepository.getPosts(_canRefresh, true).collectLatest {
            when (it) {
                is Resource.Error -> _state.value = _state.value.copy(
                    isLoading = false,
                    isRefreshing = false,
                )
                is Resource.Loading -> _state.value = _state.value.copy(
                    isLoading = false,
                    isRefreshing = true,
                )
                is Resource.Success -> _state.value = _state.value.copy(
                    isLoading = false,
                    isRefreshing = false,
                    posts = it.data,
                    hasNext = it.data.count() > 3
                )
            }
        }
    }

    fun likePost(postId: String) = viewModelScope.launch(Dispatchers.IO) {
        var isLike = false
        val newList = _state.value.posts.map {
            if (it.id == postId) {
                isLike = it.isUserLike.not()
                Post(
                    id = it.id,
                    body = it.body,
                    isUserLike = isLike,
                    likeCount = it.likeCount,
                    createdAt = it.createdAt,
                    fromUser = it.fromUser,
                    image = it.image
                )
            } else it
        }
        _state.value = _state.value.copy(
            posts = newList
        )
        exploreRepository.likePost(postId, isLike)
    }
}
package com.eneskayiklik.eventverse.feature.explore

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Divider
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavGraphBuilder
import com.eneskayiklik.eventverse.util.Screen
import com.eneskayiklik.eventverse.util.anim.ScreensAnim.enterTransition
import com.eneskayiklik.eventverse.util.anim.ScreensAnim.exitTransition
import com.eneskayiklik.eventverse.util.anim.ScreensAnim.popEnterTransition
import com.eneskayiklik.eventverse.util.anim.ScreensAnim.popExitTransition
import com.eneskayiklik.eventverse.feature.explore.component.ExploreToolbar
import com.eneskayiklik.eventverse.feature.explore.component.SinglePostView
import com.eneskayiklik.eventverse.feature.polls.component.EmptyPollsView
import com.eneskayiklik.eventverse.feature.polls.component.ListLoadingView
import com.google.accompanist.insets.statusBarsPadding
import com.google.accompanist.navigation.animation.composable
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState

@Composable
private fun ExploreScreen(
    onNavigate: (String) -> Unit,
    clearBackStack: () -> Unit,
    viewModel: ExploreViewModel = hiltViewModel()
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colors.background)
    ) {
        ExploreToolbar(
            modifier = Modifier
                .fillMaxWidth()
                .background(MaterialTheme.colors.background)
                .statusBarsPadding()
                .height(56.dp)
        ) {
            onNavigate(Screen.Profile.route(true))
        }

        Box(
            modifier = Modifier
                .fillMaxSize()
        ) {
            ItemsContent(viewModel, onNavigate)
        }
    }
}

@Composable
private fun BoxScope.ItemsContent(viewModel: ExploreViewModel, onNavigate: (String) -> Unit) {
    val state = viewModel.state.collectAsState().value
    val posts = state.posts

    if (state.showInitialLoading) {
        CircularProgressIndicator(
            modifier = Modifier
                .size(32.dp)
                .align(Alignment.Center),
            color = MaterialTheme.colors.primary,
            strokeWidth = 2.dp
        )
    } else {
        SwipeRefresh(
            state = rememberSwipeRefreshState(isRefreshing = state.isRefreshing),
            onRefresh = viewModel::refreshPosts
        ) {
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize(),
                contentPadding = PaddingValues(bottom = 56.dp),
                verticalArrangement = if (state.showEmptyScreen) Arrangement.Center else Arrangement.Top
            ) {
                if (state.showEmptyScreen) {
                    item {
                        EmptyPollsView(
                            modifier = Modifier
                                .align(Alignment.Center)
                                .padding(32.dp)
                        )
                    }
                } else {
                    items(
                        posts.count(),
                        key = { posts[it].id }) { index ->
                        val currentItem = posts[index]
                        SinglePostView(
                            post = currentItem,
                            modifier = Modifier
                                .fillMaxWidth(),
                            onPostAction = { }
                        )
                        if (index != posts.lastIndex) {
                            Divider(
                                modifier = Modifier
                                    .height(1.dp)
                                    .background(MaterialTheme.colors.secondary)
                            )
                        } else if (state.hasNext) {
                            ListLoadingView {
                                viewModel.getPolls()
                            }
                        }
                    }
                }
            }
        }
    }
}

@ExperimentalMaterialApi
@ExperimentalAnimationApi
fun NavGraphBuilder.exploreComposable(
    onNavigate: (String) -> Unit,
    clearBackStack: () -> Unit,
) {
    composable(
        route = Screen.Home.route,
        exitTransition = { exitTransition() },
        popExitTransition = { popExitTransition() },
        popEnterTransition = { popEnterTransition() },
        enterTransition = { enterTransition() },
    ) {
        ExploreScreen(onNavigate, clearBackStack)
    }
}
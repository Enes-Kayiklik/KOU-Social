package com.eneskayiklik.eventverse.feature.profile.polls

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
import androidx.navigation.NavType
import androidx.navigation.navArgument
import com.eneskayiklik.eventverse.util.Screen
import com.eneskayiklik.eventverse.util.anim.ScreensAnim.enterTransition
import com.eneskayiklik.eventverse.util.anim.ScreensAnim.exitTransition
import com.eneskayiklik.eventverse.util.anim.ScreensAnim.popEnterTransition
import com.eneskayiklik.eventverse.util.anim.ScreensAnim.popExitTransition
import com.eneskayiklik.eventverse.feature.polls.component.ListLoadingView
import com.eneskayiklik.eventverse.feature.polls.component.poll_view.SinglePollView
import com.eneskayiklik.eventverse.feature.profile.polls.component.EmptyProfilePollsView
import com.eneskayiklik.eventverse.feature.profile.polls.component.ProfilePollsToolbar
import com.google.accompanist.insets.statusBarsPadding
import com.google.accompanist.navigation.animation.composable

@Composable
private fun ProfilePollsScreen(
    onNavigate: (String) -> Unit,
    clearBackStack: () -> Unit,
    viewModel: ProfilePollsViewModel = hiltViewModel()
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colors.background)
    ) {
        ProfilePollsToolbar(
            modifier = Modifier
                .fillMaxWidth()
                .background(MaterialTheme.colors.background)
                .statusBarsPadding()
                .height(56.dp),
            onStartIconClick = clearBackStack
        )

        Box(
            modifier = Modifier
                .fillMaxSize()
        ) {
            ItemsContent(viewModel, onNavigate)
        }
    }
}

@Composable
private fun BoxScope.ItemsContent(viewModel: ProfilePollsViewModel, onNavigate: (String) -> Unit) {
    val state = viewModel.state.collectAsState().value
    val polls = state.polls

    if (state.showInitialLoading) {
        CircularProgressIndicator(
            modifier = Modifier
                .size(32.dp)
                .align(Alignment.Center),
            color = MaterialTheme.colors.primary,
            strokeWidth = 2.dp
        )
    } else {
        LazyColumn(
            modifier = Modifier
                .fillMaxSize(),
            contentPadding = PaddingValues(bottom = 56.dp),
            verticalArrangement = if (state.showEmptyScreen) Arrangement.Center else Arrangement.Top
        ) {
            if (state.showEmptyScreen) {
                item {
                    EmptyProfilePollsView(
                        modifier = Modifier
                            .fillMaxSize()
                            .align(Alignment.Center)
                            .padding(32.dp)
                    )
                }
            } else {
                items(
                    polls.count(),
                    key = { polls[it].id }) { index ->
                    val currentItem = polls[index]
                    SinglePollView(
                        poll = currentItem,
                        modifier = Modifier
                            .fillMaxWidth(),
                        onNavigate = { /* Don't navigate */ },
                        onOptionSelected = viewModel::onOptionSelected
                    )
                    if (index != polls.lastIndex) {
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

@ExperimentalMaterialApi
@ExperimentalAnimationApi
fun NavGraphBuilder.profilePollsComposable(
    onNavigate: (String) -> Unit,
    clearBackStack: () -> Unit,
) {
    composable(
        route = Screen.ProfilePolls.route,
        exitTransition = { exitTransition() },
        popExitTransition = { popExitTransition() },
        popEnterTransition = { popEnterTransition() },
        enterTransition = { enterTransition() },
        arguments = listOf(navArgument("userId") { type = NavType.StringType })
    ) {
        ProfilePollsScreen(onNavigate, clearBackStack)
    }
}
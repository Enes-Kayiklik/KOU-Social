package com.eneskayiklik.eventverse.feature.profile.attended_events

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.navArgument
import com.eneskayiklik.eventverse.feature.events.component.EmptyEventView
import com.eneskayiklik.eventverse.feature.events.component.SingleEventView
import com.eneskayiklik.eventverse.feature.profile.attended_events.component.AttendedEventsToolbar
import com.eneskayiklik.eventverse.feature.profile.attended_events.component.EmptyProfileAttendedEventsView
import com.eneskayiklik.eventverse.util.Screen
import com.eneskayiklik.eventverse.util.UiEvent
import com.eneskayiklik.eventverse.util.anim.ScreensAnim.enterTransition
import com.eneskayiklik.eventverse.util.anim.ScreensAnim.exitTransition
import com.eneskayiklik.eventverse.util.anim.ScreensAnim.popEnterTransition
import com.eneskayiklik.eventverse.util.anim.ScreensAnim.popExitTransition
import com.google.accompanist.insets.statusBarsPadding
import com.google.accompanist.navigation.animation.composable
import kotlinx.coroutines.flow.collectLatest

@Composable
private fun ProfileAttendedEventsScreen(
    onNavigate: (String) -> Unit,
    clearBackStack: () -> Unit,
    viewModel: ProfileAttendedEventsViewModel = hiltViewModel()
) {
    LaunchedEffect(key1 = true) {
        viewModel.event.collectLatest {
            when (it) {
                is UiEvent.Navigate -> {
                    when (it.id) {
                        Screen.Home.route -> clearBackStack()
                    }
                    onNavigate(it.id)
                }
                UiEvent.ClearBackStack -> clearBackStack()
                else -> Unit
            }
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colors.background),
        contentAlignment = Alignment.Center
    ) {
        Column(
            modifier = Modifier.fillMaxSize()
        ) {
            AttendedEventsToolbar(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(MaterialTheme.colors.background)
                    .statusBarsPadding()
                    .height(56.dp),
                onStartIconClick = {
                    clearBackStack()
                }
            )

            Box(
                modifier = Modifier
                    .fillMaxSize()
            ) {
                ItemsContent(viewModel, onNavigate)
            }
        }
    }
}

@Composable
private fun BoxScope.ItemsContent(
    viewModel: ProfileAttendedEventsViewModel,
    onNavigate: (String) -> Unit
) {
    val state = viewModel.state.collectAsState().value
    val posts = state.events

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
            contentPadding = PaddingValues(bottom = 56.dp, top = 10.dp),
            verticalArrangement = if (state.showEmptyScreen) Arrangement.Center else Arrangement.Top
        ) {
            if (state.showEmptyScreen) {
                item {
                    EmptyProfileAttendedEventsView(
                        modifier = Modifier
                            .fillMaxSize()
                            .align(Alignment.Center)
                            .padding(32.dp)
                    )
                }
            } else {
                items(posts.count()) { index ->
                    val currentItem = posts[index]
                    SingleEventView(
                        event = currentItem,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 16.dp)
                            .padding(bottom = 10.dp)
                            .clip(RoundedCornerShape(10.dp))
                            .border(
                                1.5.dp,
                                MaterialTheme.colors.secondary,
                                RoundedCornerShape(10.dp)
                            )
                            .clickable {
                                onNavigate(Screen.EventDetail.route(currentItem.id))
                            }
                    )
                }
            }
        }
    }
}

@ExperimentalAnimationApi
fun NavGraphBuilder.profileAttendedEventsComposable(
    onNavigate: (String) -> Unit,
    clearBackStack: () -> Unit,
) {
    composable(
        route = Screen.AttendedEvents.route,
        exitTransition = { exitTransition() },
        popExitTransition = { popExitTransition() },
        popEnterTransition = { popEnterTransition() },
        enterTransition = { enterTransition() },
        arguments = listOf(navArgument("userId") { type = NavType.StringType })
    ) {
        ProfileAttendedEventsScreen(onNavigate, clearBackStack)
    }
}
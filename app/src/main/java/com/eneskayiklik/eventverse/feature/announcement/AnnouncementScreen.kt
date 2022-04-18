package com.eneskayiklik.eventverse.feature.announcement

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Divider
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.ViewModelStoreOwner
import androidx.navigation.NavGraphBuilder
import com.eneskayiklik.eventverse.util.Screen
import com.eneskayiklik.eventverse.util.anim.ScreensAnim.enterTransition
import com.eneskayiklik.eventverse.util.anim.ScreensAnim.exitTransition
import com.eneskayiklik.eventverse.util.anim.ScreensAnim.popEnterTransition
import com.eneskayiklik.eventverse.util.anim.ScreensAnim.popExitTransition
import com.eneskayiklik.eventverse.feature.announcement.component.AnnouncementPopup
import com.eneskayiklik.eventverse.feature.announcement.component.AnnouncementToolbar
import com.eneskayiklik.eventverse.feature.announcement.component.EmptyAnnouncementView
import com.eneskayiklik.eventverse.feature.announcement.component.SingleAnnouncementView
import com.google.accompanist.insets.statusBarsPadding
import com.google.accompanist.navigation.animation.composable
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState

@Composable
private fun AnnouncementScreen(
    onNavigate: (String) -> Unit,
    clearBackStack: () -> Unit,
    viewModel: AnnouncementViewModel = hiltViewModel()
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colors.background)
    ) {
        AnnouncementToolbar(
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
private fun BoxScope.ItemsContent(viewModel: AnnouncementViewModel, onNavigate: (String) -> Unit) {
    val state = viewModel.state.collectAsState().value
    val announcements = state.announcements
    val isRefreshing = state.isRefreshing

    if (state.isPopupActive) AnnouncementPopup(state.activeAnnouncement, viewModel::closePopup)

    if (state.isLoading) {
        CircularProgressIndicator(
            modifier = Modifier
                .size(32.dp)
                .align(Alignment.Center),
            color = MaterialTheme.colors.primary,
            strokeWidth = 2.dp
        )
    } else {
        SwipeRefresh(
            state = rememberSwipeRefreshState(isRefreshing = isRefreshing),
            onRefresh = viewModel::refreshAnnouncements
        ) {
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize(),
                contentPadding = PaddingValues(bottom = 56.dp),
                verticalArrangement = if (state.showEmptyContent) Arrangement.Center else Arrangement.Top
            ) {
                if (state.showEmptyContent) {
                    item {
                        EmptyAnnouncementView(
                            modifier = Modifier
                                .align(Alignment.Center)
                                .padding(32.dp)
                        ) {
                            onNavigate(Screen.EditProfile.route)
                        }
                    }
                } else {
                    items(
                        announcements.count(),
                        key = { announcements[it].hashCode() }) { index ->
                        val currentItem = announcements[index]
                        SingleAnnouncementView(
                            announcement = currentItem,
                            modifier = Modifier
                                .fillMaxWidth(),
                            onClick = viewModel::showAnnouncementPopup
                        )
                        if (index != announcements.lastIndex) {
                            Divider(
                                modifier = Modifier
                                    .height(1.dp)
                                    .background(MaterialTheme.colors.secondary)
                            )
                        }
                    }
                }
            }
        }
    }
}

@ExperimentalAnimationApi
fun NavGraphBuilder.announcementComposable(
    onNavigate: (String) -> Unit,
    clearBackStack: () -> Unit,
    owner: ViewModelStoreOwner
) {
    composable(
        route = Screen.Announcement.route,
        exitTransition = { exitTransition() },
        popExitTransition = { popExitTransition() },
        popEnterTransition = { popEnterTransition() },
        enterTransition = { enterTransition() },
    ) {
        AnnouncementScreen(onNavigate, clearBackStack, hiltViewModel(owner))
    }
}
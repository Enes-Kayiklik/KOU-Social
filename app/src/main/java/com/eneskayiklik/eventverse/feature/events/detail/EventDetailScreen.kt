package com.eneskayiklik.eventverse.feature.events.detail

import android.app.Activity
import android.content.Intent
import android.widget.Toast
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.navArgument
import com.eneskayiklik.eventverse.R
import com.eneskayiklik.eventverse.core.presentation.MainActivity
import com.eneskayiklik.eventverse.data.model.event_detail.pages
import com.eneskayiklik.eventverse.feature.events.component.EventDetailToolbar
import com.eneskayiklik.eventverse.feature.events.detail.lazy_items.detailImage
import com.eneskayiklik.eventverse.feature.events.detail.lazy_items.detailThings
import com.eneskayiklik.eventverse.feature.events.detail.lazy_items.eventTitle
import com.eneskayiklik.eventverse.feature.events.detail.lazy_items.organizerSection
import com.eneskayiklik.eventverse.feature.events.detail.lazy_items.tab.tabSection
import com.eneskayiklik.eventverse.util.Screen
import com.eneskayiklik.eventverse.util.UiEvent
import com.eneskayiklik.eventverse.util.anim.ScreensAnim.enterTransition
import com.eneskayiklik.eventverse.util.anim.ScreensAnim.exitTransition
import com.eneskayiklik.eventverse.util.anim.ScreensAnim.popEnterTransition
import com.eneskayiklik.eventverse.util.anim.ScreensAnim.popExitTransition
import com.google.accompanist.insets.statusBarsPadding
import com.google.accompanist.navigation.animation.composable
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.rememberPagerState
import kotlinx.coroutines.flow.collectLatest

@OptIn(
    ExperimentalMaterialApi::class,
    ExperimentalFoundationApi::class,
    ExperimentalAnimationApi::class,
    ExperimentalPagerApi::class
)
@Composable
private fun EventDetailScreen(
    onNavigate: (String) -> Unit,
    clearBackStack: () -> Unit,
    viewModel: EventDetailViewModel = hiltViewModel()
) {
    val state = viewModel.state.collectAsState().value
    val event = state.event
    val context = LocalContext.current

    // We are going to use this for event detail pager
    val pagerState = rememberPagerState()
    val coroutineScope = rememberCoroutineScope()

    LaunchedEffect(key1 = true) {
        viewModel.event.collectLatest {
            when (it) {
                is UiEvent.RestartApp -> {
                    context.startActivity(Intent(context, MainActivity::class.java))
                    (context as? Activity)?.finish()
                }
                is UiEvent.Navigate -> onNavigate(it.id)
                UiEvent.ClearBackStack -> clearBackStack()
                is UiEvent.Toast -> Toast.makeText(context, it.message, Toast.LENGTH_LONG).show()
                else -> Unit
            }
        }
    }

    // Page selected callback
    LaunchedEffect(pagerState) {
        snapshotFlow { pagerState.currentPage }.collect { page ->
            when (page) {
                1 -> viewModel.getReviews()
            }
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colors.background)
    ) {
        if (state.isLoading) CircularProgressIndicator(
            modifier = Modifier
                .size(32.dp)
                .align(Alignment.Center),
            color = MaterialTheme.colors.primary,
            strokeWidth = 2.dp
        )

        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colors.background)
        ) {
            EventDetailToolbar(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(MaterialTheme.colors.background)
                    .statusBarsPadding()
                    .height(56.dp),
                onStartIconClick = clearBackStack,
            )
            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                contentPadding = PaddingValues(bottom = 84.dp)
            ) {
                detailImage(event.coverImage, state.date, state.showTimer)
                item { Spacer(modifier = Modifier.height(20.dp)) }
                eventTitle(event.title, event.isLiked, event.dayMonth) {
                    viewModel.likeEvent(event.id)
                }
                item { Spacer(modifier = Modifier.height(10.dp)) }
                organizerSection(event.user) {
                    onNavigate(Screen.Profile.route(userId = event.user.userId))
                }
                item {
                    Spacer(modifier = Modifier.height(10.dp))
                    Divider(
                        modifier = Modifier
                            .height(1.dp)
                            .background(MaterialTheme.colors.secondary)
                    )
                    Spacer(modifier = Modifier.height(10.dp))
                }
                detailThings(event)
                item {
                    Spacer(modifier = Modifier.height(10.dp))
                    Divider(
                        modifier = Modifier
                            .height(1.dp)
                            .background(MaterialTheme.colors.secondary)
                    )
                }
                tabSection(state, viewModel::onEvent, pagerState, coroutineScope, pages)
            }
        }

        Row(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .border(1.5.dp, MaterialTheme.colors.secondary)
                .background(MaterialTheme.colors.background)
                .padding(vertical = 20.dp, horizontal = 16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = if (event.isAttended) stringResource(id = R.string.not_join_event_title)
                else stringResource(id = R.string.join_event_title),
                style = MaterialTheme.typography.h1.copy(
                    color = MaterialTheme.colors.onBackground,
                    fontSize = 20.sp
                )
            )
            Spacer(modifier = Modifier.weight(1F))
            Button(onClick = { viewModel.attendEvent(event.id) }) {
                Text(
                    text = if (event.isAttended) stringResource(id = R.string.not_join_button)
                    else stringResource(id = R.string.join_button),
                    style = MaterialTheme.typography.h4.copy(
                        color = MaterialTheme.colors.onPrimary,
                        fontSize = 16.sp
                    )
                )
            }
        }
    }
}

@ExperimentalAnimationApi
fun NavGraphBuilder.eventDetailComposable(
    onNavigate: (String) -> Unit,
    clearBackStack: () -> Unit,
) {
    composable(
        route = Screen.EventDetail.route,
        exitTransition = { exitTransition() },
        popExitTransition = { popExitTransition() },
        popEnterTransition = { popEnterTransition() },
        enterTransition = { enterTransition() },
        arguments = listOf(navArgument("eventId") { type = NavType.StringType })
    ) {
        EventDetailScreen(onNavigate, clearBackStack)
    }
}
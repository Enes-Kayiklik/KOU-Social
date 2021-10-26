package com.eneskayiklik.eventverse.feature_explore.presentation

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.core.tween
import androidx.compose.animation.slideInVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.MaterialTheme
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Settings
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavGraphBuilder
import com.eneskayiklik.eventverse.R
import com.eneskayiklik.eventverse.core.util.Screen
import com.eneskayiklik.eventverse.feature_explore.presentation.component.EventverseAppbar
import com.eneskayiklik.eventverse.feature_explore.presentation.component.HeaderSection
import com.eneskayiklik.eventverse.feature_explore.presentation.component.upcoming.SingleUpcomingPage
import com.google.accompanist.navigation.animation.composable
import com.google.accompanist.pager.ExperimentalPagerApi

@ExperimentalMaterialApi
@ExperimentalPagerApi
@Composable
private fun ExploreScreen(
    onNavigate: (String) -> Unit,
    clearBackStack: () -> Unit,
    viewModel: ExploreViewModel = hiltViewModel()
) {
    val upcoming = viewModel.upcomingEvents.collectAsState().value
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colors.background)
    ) {
        EventverseAppbar(
            modifier = Modifier
                .fillMaxWidth()
                .background(MaterialTheme.colors.primary)
                .padding(top = 20.dp)
                .height(60.dp),
            startIcon = Icons.Rounded.Settings,
            endIcon = painterResource(id = R.drawable.ic_ticket),
            onStartIconClick = { },
            onEndIconClick = { }
        )
        LazyColumn {
            item {
                Spacer(modifier = Modifier.height(10.dp))
                HeaderSection(modifier = Modifier.padding(start = 16.dp), title = "Upcoming Events")
                Spacer(modifier = Modifier.height(10.dp))
            }

            item {
                LazyRow(
                    contentPadding = PaddingValues(horizontal = 16.dp, vertical = 2.dp),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    items(upcoming) { data ->
                        SingleUpcomingPage(data = data) {

                        }
                    }
                }
            }
        }
    }
}

@ExperimentalMaterialApi
@ExperimentalPagerApi
@ExperimentalAnimationApi
fun NavGraphBuilder.exploreComposable(
    onNavigate: (String) -> Unit,
    clearBackStack: () -> Unit,
) {
    composable(
        route = Screen.Explore.route,
        enterTransition = { initial, _ ->
            val comingFrom = initial.destination.route ?: ""
            val condition =
                comingFrom.startsWith(Screen.Login.route) || comingFrom.startsWith(
                    Screen.SelectInterest.route
                )
            if (condition)
                slideInVertically(
                    initialOffsetY = { fullWidth -> fullWidth },
                    animationSpec = tween(durationMillis = 300)
                ) else null
        }
    ) {
        ExploreScreen(onNavigate, clearBackStack)
    }
}
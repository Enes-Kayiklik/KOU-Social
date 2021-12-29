package com.eneskayiklik.eventverse.feature_explore.presentation

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.core.tween
import androidx.compose.animation.slideInVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.MaterialTheme
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Bookmarks
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavGraphBuilder
import com.eneskayiklik.eventverse.core.util.Screen
import com.eneskayiklik.eventverse.feature_explore.presentation.component.EventverseAppbar
import com.eneskayiklik.eventverse.feature_explore.presentation.component.popular_now.PopularNowSection
import com.eneskayiklik.eventverse.feature_explore.presentation.component.select_location.SelectLocationSection
import com.google.accompanist.insets.statusBarsPadding
import com.google.accompanist.navigation.animation.composable

@ExperimentalMaterialApi
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
                .statusBarsPadding()
                .height(60.dp),
            endIcon = Icons.Rounded.Bookmarks,
            onStartIconClick = { },
            onEndIconClick = { }
        )

        LazyColumn(
            contentPadding = PaddingValues(bottom = 50.dp)
        ) {
            item {
                Spacer(modifier = Modifier.height(10.dp))
                SelectLocationSection(
                    modifier = Modifier.fillMaxWidth(),
                    selectedLocation = "Osmaniye"
                ) {
                    // TODO("select city on click")
                }
            }

            item {
                Spacer(modifier = Modifier.height(10.dp))
                PopularNowSection(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 16.dp),
                    items = upcoming
                ) {
                    // TODO("On item selected")
                }
            }

            item {
                Spacer(modifier = Modifier.height(10.dp))
                PopularNowSection(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 16.dp),
                    items = upcoming
                ) {
                    // TODO("On item selected")
                }
            }

            item {
                Spacer(modifier = Modifier.height(10.dp))
                PopularNowSection(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 16.dp),
                    items = upcoming
                ) {
                    // TODO("On item selected")
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
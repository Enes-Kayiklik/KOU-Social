package com.eneskayiklik.eventverse.feature.explore

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
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
import com.google.accompanist.insets.statusBarsPadding
import com.google.accompanist.navigation.animation.composable

@ExperimentalMaterialApi
@Composable
private fun ExploreScreen(
    onNavigate: (String) -> Unit,
    clearBackStack: () -> Unit,
    viewModel: ExploreViewModel = hiltViewModel()
) {
    Column(
        modifier = Modifier.fillMaxSize()
            .background(MaterialTheme.colors.background)
    ) {
        ExploreToolbar(
            modifier = Modifier
                .fillMaxWidth()
                .background(MaterialTheme.colors.background)
                .padding(horizontal = 8.dp)
                .statusBarsPadding()
                .height(56.dp),
            onStartIconClick = { },
            onEndIconClick = { onNavigate(Screen.Profile.route(true)) }
        )

        /*LazyColumn(
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
        }*/
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
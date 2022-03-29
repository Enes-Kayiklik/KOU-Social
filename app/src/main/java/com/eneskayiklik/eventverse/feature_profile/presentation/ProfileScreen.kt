package com.eneskayiklik.eventverse.feature_profile.presentation

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.navArgument
import com.eneskayiklik.eventverse.core.util.Screen
import com.eneskayiklik.eventverse.core.util.anim.ScreensAnim.enterTransition
import com.eneskayiklik.eventverse.core.util.anim.ScreensAnim.exitTransition
import com.eneskayiklik.eventverse.core.util.anim.ScreensAnim.popEnterTransition
import com.eneskayiklik.eventverse.core.util.anim.ScreensAnim.popExitTransition
import com.eneskayiklik.eventverse.feature_profile.presentation.component.ProfileToolbar
import com.eneskayiklik.eventverse.feature_profile.presentation.component.UserInfoSection
import com.google.accompanist.insets.statusBarsPadding
import com.google.accompanist.navigation.animation.composable

@Composable
private fun ProfileScreen(
    onNavigate: (String) -> Unit,
    clearBackStack: () -> Unit,
    viewModel: ProfileViewModel = hiltViewModel()
) {
    val state = viewModel.state.collectAsState().value
    val user = state.user
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colors.surface)
    ) {
        ProfileToolbar(
            modifier = Modifier
                .fillMaxWidth()
                .background(MaterialTheme.colors.background)
                .statusBarsPadding()
                .height(60.dp),
            onStartIconClick = clearBackStack,
            onEndIconClick = { }
        )
        LazyColumn(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1F),
            horizontalAlignment = Alignment.CenterHorizontally,
            contentPadding = PaddingValues(vertical = 20.dp)
        ) {
            if (user != null)
                item {
                    UserInfoSection(
                        user.profilePic,
                        user.fullName,
                        user.department
                    )
                }
            // Social accounts section

            // post list button
            // reviews button
            // created events
        }
        Button(
            onClick = { /*TODO*/ },
            Modifier
                .fillMaxWidth()
                .height(50.dp)
        ) {
            Text(text = "Log out")
        }
    }
}

@ExperimentalAnimationApi
fun NavGraphBuilder.profileComposable(
    onNavigate: (String) -> Unit,
    clearBackStack: () -> Unit,
) {
    composable(
        route = Screen.Profile.route,
        exitTransition = { exitTransition() },
        popExitTransition = { popExitTransition() },
        popEnterTransition = { popEnterTransition() },
        enterTransition = { enterTransition() },
        arguments = listOf(navArgument("userId") { type = NavType.StringType })
    ) {
        ProfileScreen(onNavigate, clearBackStack)
    }
}
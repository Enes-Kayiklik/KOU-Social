package com.eneskayiklik.eventverse.core.component

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.ScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import com.eneskayiklik.eventverse.core.util.Screen
import com.eneskayiklik.eventverse.feature_auth.presentation.interest.selectInterestComposable
import com.eneskayiklik.eventverse.feature_auth.presentation.login.loginComposable
import com.eneskayiklik.eventverse.feature_auth.presentation.signup.signupComposable
import com.eneskayiklik.eventverse.feature_auth.presentation.splash.splashComposable
import com.eneskayiklik.eventverse.feature_timeline.presentation.timelineComposable
import com.google.accompanist.insets.navigationBarsWithImePadding
import com.google.accompanist.navigation.animation.AnimatedNavHost

@ExperimentalMaterialApi
@ExperimentalFoundationApi
@ExperimentalAnimationApi
@Composable
fun AnimatedNavigation(
    navController: NavHostController,
    scaffoldState: ScaffoldState
) {
    AnimatedNavHost(
        modifier = Modifier.navigationBarsWithImePadding(),
        navController = navController,
        startDestination = Screen.Splash.route
    ) {
        splashComposable(navController::navigate, navController::popBackStack)
        loginComposable(navController::navigate, navController::popBackStack)
        signupComposable(navController::navigate, navController::popBackStack)
        timelineComposable(navController::navigate, navController::popBackStack)
        selectInterestComposable(
            navController::navigate,
            navController::popBackStack,
            scaffoldState
        )
    }
}
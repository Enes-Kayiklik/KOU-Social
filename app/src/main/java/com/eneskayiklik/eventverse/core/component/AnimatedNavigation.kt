package com.eneskayiklik.eventverse.core.component

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.ScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.LocalViewModelStoreOwner
import androidx.navigation.NavHostController
import com.eneskayiklik.eventverse.util.Screen
import com.eneskayiklik.eventverse.feature.auth.introduction.introComposable
import com.eneskayiklik.eventverse.feature.auth.login.loginComposable
import com.eneskayiklik.eventverse.feature.auth.signup.signupComposable
import com.eneskayiklik.eventverse.feature.auth.splash.splashComposable
import com.eneskayiklik.eventverse.feature.polls.pollsComposable
import com.eneskayiklik.eventverse.feature.create.createComposable
import com.eneskayiklik.eventverse.feature.explore.exploreComposable
import com.eneskayiklik.eventverse.feature.announcement.announcementComposable
import com.eneskayiklik.eventverse.feature.profile.settings.edit_profile.editProfileComposable
import com.eneskayiklik.eventverse.feature.meal.mealComposable
import com.eneskayiklik.eventverse.feature.polls.create_poll.createPollComposable
import com.eneskayiklik.eventverse.feature.profile.profile.profileComposable
import com.eneskayiklik.eventverse.feature.profile.settings.delete_account.deleteAccountComposable
import com.eneskayiklik.eventverse.feature.profile.settings.settings.settingsComposable
import com.eneskayiklik.eventverse.feature.profile.settings.update_password.updatePasswordComposable
import com.eneskayiklik.eventverse.feature.share.shareComposable
import com.google.accompanist.insets.navigationBarsWithImePadding
import com.google.accompanist.navigation.animation.AnimatedNavHost

@ExperimentalMaterialApi
@ExperimentalFoundationApi
@ExperimentalAnimationApi
@Composable
fun BaseAnimatedNavigation(
    navController: NavHostController,
    scaffoldState: ScaffoldState
) {
    val navigateSingleTop: (String) -> Unit = {
        navController.navigate(it) {
            launchSingleTop = true
        }
    }
    val viewModelStoreOwner = checkNotNull(LocalViewModelStoreOwner.current) {
        "No ViewModelStoreOwner was provided via LocalViewModelStoreOwner"
    }
    AnimatedNavHost(
        modifier = Modifier.navigationBarsWithImePadding(),
        navController = navController,
        startDestination = Screen.Splash.route
    ) {
        splashComposable({
            navController.navigate(it) {
                popUpTo(0)
            }
        }, navController::popBackStack)
        introComposable(navController::navigate, navController::popBackStack)
        loginComposable({
            navController.navigate(it) {
                popUpTo(0)
            }
        }, navController::popBackStack)
        signupComposable({
            navController.navigate(it) {
                popUpTo(0)
            }
        }, navController::popBackStack, scaffoldState)
        exploreComposable(navigateSingleTop, navController::popBackStack)
        pollsComposable(navigateSingleTop, navController::popBackStack, viewModelStoreOwner)
        createPollComposable(navigateSingleTop, navController::popBackStack)
        announcementComposable(navigateSingleTop, navController::popBackStack, viewModelStoreOwner)
        mealComposable(navigateSingleTop, navController::popBackStack, viewModelStoreOwner)
        createComposable(navigateSingleTop, navController::popBackStack, scaffoldState)
        shareComposable(navigateSingleTop, navController::popBackStack, scaffoldState)
        settingsComposable(navigateSingleTop, navController::popBackStack)
        editProfileComposable(navigateSingleTop, navController::popBackStack)
        profileComposable(navigateSingleTop, navController::popBackStack)
        updatePasswordComposable(navigateSingleTop, navController::popBackStack)
        deleteAccountComposable(navigateSingleTop, navController::popBackStack)
    }
}
package com.eneskayiklik.eventverse.core.presentation

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatDelegate
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.navigation.NavBackStackEntry
import androidx.navigation.compose.currentBackStackEntryAsState
import com.eneskayiklik.eventverse.core.component.BaseAnimatedNavigation
import com.eneskayiklik.eventverse.core.component.BaseScaffold
import com.eneskayiklik.eventverse.core.ui.theme.EventverseTheme
import com.eneskayiklik.eventverse.core.util.Screen
import com.google.accompanist.insets.ProvideWindowInsets
import com.google.accompanist.navigation.animation.rememberAnimatedNavController
import dagger.hilt.android.AndroidEntryPoint

@ExperimentalMaterialApi
@AndroidEntryPoint
@ExperimentalFoundationApi
@ExperimentalAnimationApi
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val isDarkTheme = isSystemInDarkTheme()
            var theme by remember { mutableStateOf(false) }
            EventverseTheme(theme) {
                ProvideWindowInsets(windowInsetsAnimationsEnabled = true) {
                    MainScreen {
                        theme = theme.not()
                        if (isDarkTheme) setDayTheme() else setDarkTheme()
                    }
                }
            }
        }
    }

    private fun setDayTheme() {
        Log.e("TAG", "onCreate: 1", )
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
    }

    private fun setDarkTheme() {
        Log.e("TAG", "onCreate: 2", )
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
    }
}

@ExperimentalFoundationApi
@ExperimentalMaterialApi
@ExperimentalAnimationApi
@Composable
private fun MainScreen(toggleTheme: () -> Unit) {
    Surface(color = MaterialTheme.colors.background) {
        val navController = rememberAnimatedNavController()
        val navBackStackEntry by navController.currentBackStackEntryAsState()
        val scaffoldState = rememberScaffoldState()
        BaseScaffold(
            modifier = Modifier.fillMaxSize(),
            navController = navController,
            showBottomBar = shouldShowBottomBar(navBackStackEntry),
            state = scaffoldState,
            onFabClick = {
                if (navController.currentDestination?.route != Screen.CreateEvent.route) navController.navigate(
                    Screen.Share.route
                )
            }
        ) {
            BaseAnimatedNavigation(navController, scaffoldState, toggleTheme)
        }
    }
}

private fun shouldShowBottomBar(backStackEntry: NavBackStackEntry?): Boolean {
    return backStackEntry?.destination?.route in listOf(
        Screen.Home.route,
        Screen.Announcement.route,
        Screen.Polls.route,
        Screen.Meal.route
    )
}
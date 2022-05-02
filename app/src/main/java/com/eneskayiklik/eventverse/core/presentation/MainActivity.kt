package com.eneskayiklik.eventverse.core.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavBackStackEntry
import androidx.navigation.compose.currentBackStackEntryAsState
import com.eneskayiklik.eventverse.core.component.BaseAnimatedNavigation
import com.eneskayiklik.eventverse.core.component.BaseScaffold
import com.eneskayiklik.eventverse.core.ui.theme.EventverseTheme
import com.eneskayiklik.eventverse.data.model.profile.DarkMode
import com.eneskayiklik.eventverse.data.model.profile.Language
import com.eneskayiklik.eventverse.util.Screen
import com.eneskayiklik.eventverse.util.data_store.AppDataStore
import com.google.accompanist.insets.ProvideWindowInsets
import com.google.accompanist.navigation.animation.rememberAnimatedNavController
import dagger.hilt.android.AndroidEntryPoint
import java.util.*
import javax.inject.Inject

@AndroidEntryPoint
@OptIn(
    ExperimentalAnimationApi::class,
    ExperimentalFoundationApi::class,
    ExperimentalMaterialApi::class
)
class MainActivity : ComponentActivity() {
    @Inject
    lateinit var dataStore: AppDataStore

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val theme = dataStore.activeMode.collectAsState(initial = DarkMode.FOLLOW_SYSTEM).value
            val language = dataStore.activeLanguage.collectAsState(initial = Language.UNDEFINED).value
            SetLanguage(language = language)

            EventverseTheme(theme) {
                ProvideWindowInsets(windowInsetsAnimationsEnabled = true) {
                    MainScreen()
                }
            }
        }
    }
}

@Composable
private fun SetLanguage(language: Language) {
    if (language == Language.UNDEFINED) return
    val locale = Locale(language.key)
    val config = LocalConfiguration.current
    config.setLocale(locale)
    val res = LocalContext.current.resources
    res.updateConfiguration(config, res.displayMetrics)
}

@ExperimentalFoundationApi
@ExperimentalMaterialApi
@ExperimentalAnimationApi
@Composable
private fun MainScreen() {
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
            BaseAnimatedNavigation(navController, scaffoldState)
        }
    }
}

private fun shouldShowBottomBar(backStackEntry: NavBackStackEntry?): Boolean {
    return backStackEntry?.destination?.route in listOf(
        Screen.Home.route,
        Screen.Events.route,
        Screen.Announcement.route,
        Screen.Polls.route,
        Screen.Meal.route
    )
}
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
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
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
import io.getstream.chat.android.client.ChatClient
import javax.inject.Inject

@ExperimentalMaterialApi
@AndroidEntryPoint
@ExperimentalFoundationApi
@ExperimentalAnimationApi
class MainActivity : ComponentActivity() {
    @Inject
    lateinit var client: ChatClient
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            EventverseTheme {
                ProvideWindowInsets(windowInsetsAnimationsEnabled = true) {
                    MainScreen()
                }
            }
        }
    }

    override fun onDestroy() {
        client.disconnect()
        super.onDestroy()
    }
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
        Screen.Explore.route,
        Screen.Map.route,
        Screen.MessageList.route,
        Screen.Calendar.route,
        Screen.Profile.route(true)
    )
}
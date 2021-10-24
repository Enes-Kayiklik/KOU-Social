package com.eneskayiklik.eventverse.core.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import com.eneskayiklik.eventverse.core.component.AnimatedNavigation
import com.eneskayiklik.eventverse.core.ui.theme.EventverseTheme
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
            EventverseTheme {
                ProvideWindowInsets(windowInsetsAnimationsEnabled = true) {
                    MainScreen()
                }
            }
        }
    }
}

@ExperimentalFoundationApi
@ExperimentalMaterialApi
@ExperimentalAnimationApi
@Composable
private fun MainScreen() {
    Surface(color = MaterialTheme.colors.background) {
        val navController = rememberAnimatedNavController()
        val scaffoldState = rememberScaffoldState()
        Scaffold(
            scaffoldState = scaffoldState
        ) {
            AnimatedNavigation(navController, scaffoldState)
        }
    }
}
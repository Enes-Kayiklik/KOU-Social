package com.eneskayiklik.eventverse.core.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Surface
import com.eneskayiklik.eventverse.core.component.AnimatedNavigation
import com.eneskayiklik.eventverse.core.ui.theme.EventverseTheme
import com.google.accompanist.navigation.animation.rememberAnimatedNavController
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    @ExperimentalAnimationApi
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            EventverseTheme {
                Surface(color = MaterialTheme.colors.background) {
                    Scaffold {
                        val navController = rememberAnimatedNavController()
                        AnimatedNavigation(navController)
                    }
                }
            }
        }
    }
}
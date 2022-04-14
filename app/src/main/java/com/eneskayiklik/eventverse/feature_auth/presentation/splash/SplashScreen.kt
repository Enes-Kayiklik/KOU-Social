package com.eneskayiklik.eventverse.feature_auth.presentation.splash

import android.content.res.Configuration
import android.widget.Toast
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavGraphBuilder
import com.eneskayiklik.eventverse.R
import com.eneskayiklik.eventverse.core.component.InfoDialog
import com.eneskayiklik.eventverse.core.util.Screen
import com.eneskayiklik.eventverse.core.util.UiEvent
import com.eneskayiklik.eventverse.core.util.anim.ScreensAnim.enterTransition
import com.eneskayiklik.eventverse.core.util.anim.ScreensAnim.exitTransition
import com.eneskayiklik.eventverse.core.util.anim.ScreensAnim.popEnterTransition
import com.eneskayiklik.eventverse.core.util.anim.ScreensAnim.popExitTransition
import com.google.accompanist.navigation.animation.composable
import kotlinx.coroutines.flow.collectLatest

@Composable
private fun SplashScreen(
    onNavigate: (String) -> Unit,
    clearBackStack: () -> Unit,
    viewModel: SplashViewModel = hiltViewModel()
) {
    var startAnimation by remember { mutableStateOf(false) }
    val state = viewModel.state.collectAsState().value
    val context = LocalContext.current

    val offsetState by animateDpAsState(
        targetValue = if (startAnimation) 0.dp else 100.dp,
        animationSpec = tween(
            durationMillis = 500,
            easing = FastOutSlowInEasing
        )
    )
    val alphaState by animateFloatAsState(
        targetValue = if (startAnimation) 1F else 0F,
        animationSpec = tween(
            durationMillis = 500
        )
    )

    LaunchedEffect(key1 = Unit) {
        startAnimation = true
        viewModel.initUser()
        viewModel.uiEvent.collectLatest {
            when (it) {
                is UiEvent.ShowSnackbar -> Unit
                is UiEvent.Navigate -> onNavigate(it.id)
                UiEvent.ClearBackStack -> clearBackStack()
                is UiEvent.Toast -> {
                    Toast.makeText(context, it.message, Toast.LENGTH_LONG).show()
                }
                else -> Unit
            }
        }
    }

    if (state.dialogState != null) InfoDialog(state = state.dialogState)

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colors.primary)
    ) {
        Image(
            modifier = Modifier
                .size(200.dp)
                .offset(y = offsetState)
                .alpha(alpha = alphaState)
                .align(Alignment.Center),
            painter = painterResource(id = R.drawable.ic_launcher_foreground),
            contentDescription = stringResource(id = R.string.app_logo)
        )

        CircularProgressIndicator(
            modifier = Modifier
                .padding(30.dp)
                .size(20.dp)
                .align(Alignment.BottomCenter),
            color = MaterialTheme.colors.onPrimary,
            strokeWidth = 2.dp
        )
    }
}

@ExperimentalAnimationApi
fun NavGraphBuilder.splashComposable(
    onNavigate: (String) -> Unit,
    clearBackStack: () -> Unit,
) {
    composable(
        route = Screen.Splash.route,
        exitTransition = { exitTransition() },
        popExitTransition = { popExitTransition() },
        popEnterTransition = { popEnterTransition() },
        enterTransition = { enterTransition() },
    ) {
        SplashScreen(onNavigate, clearBackStack)
    }
}

@Preview(
    name = "Night Mode",
    uiMode = Configuration.UI_MODE_NIGHT_YES,
)
@Preview(
    name = "Day Mode",
    uiMode = Configuration.UI_MODE_NIGHT_NO,
)
@Composable
fun Preview() {
    SplashScreen(onNavigate = { }, clearBackStack = { })
}
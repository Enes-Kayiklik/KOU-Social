package com.eneskayiklik.eventverse.feature_auth.presentation.introduction

import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavGraphBuilder
import com.eneskayiklik.eventverse.R
import com.eneskayiklik.eventverse.core.component.InfoDialog
import com.eneskayiklik.eventverse.core.util.GOOGLE_LOGIN_KEY
import com.eneskayiklik.eventverse.core.util.Screen
import com.eneskayiklik.eventverse.core.util.UiEvent
import com.eneskayiklik.eventverse.core.util.anim.ScreensAnim.enterTransition
import com.eneskayiklik.eventverse.core.util.anim.ScreensAnim.exitTransition
import com.eneskayiklik.eventverse.core.util.anim.ScreensAnim.popEnterTransition
import com.eneskayiklik.eventverse.core.util.anim.ScreensAnim.popExitTransition
import com.eneskayiklik.eventverse.core.util.contract.GoogleLoginContract
import com.eneskayiklik.eventverse.feature_auth.data.event.IntroEvent
import com.eneskayiklik.eventverse.feature_auth.presentation.introduction.component.AlreadyHaveAccountText
import com.eneskayiklik.eventverse.feature_auth.presentation.introduction.component.IntroButton
import com.eneskayiklik.eventverse.feature_auth.presentation.introduction.util.getIntroTitleString
import com.google.accompanist.navigation.animation.composable
import com.google.android.gms.common.api.ApiException
import kotlinx.coroutines.flow.collectLatest

@ExperimentalMaterialApi
@ExperimentalAnimationApi
@Composable
private fun IntroScreen(
    onNavigate: (String) -> Unit,
    clearBackStack: () -> Unit,
    viewModel: IntroViewModel = hiltViewModel()
) {
    val state = viewModel.state.collectAsState().value

    val contract = rememberLauncherForActivityResult(contract = GoogleLoginContract()) { task ->
        try {
            val account = task?.getResult(ApiException::class.java)
            viewModel.loginWithGoogle(account ?: return@rememberLauncherForActivityResult)
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    LaunchedEffect(key1 = true) {
        viewModel.uiEvent.collectLatest {
            when (it) {
                is UiEvent.ShowSnackbar -> Unit
                is UiEvent.Navigate -> onNavigate(it.id)
                UiEvent.ClearBackStack -> clearBackStack()
            }
        }
    }

    if (state.errorDialogState != null) InfoDialog(state = state.errorDialogState)

    Box(
        modifier = Modifier
            .background(MaterialTheme.colors.background)
            .fillMaxSize()
    ) {
        Image(
            painter = painterResource(id = R.drawable.ic_login_background),
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier.fillMaxSize()
        )
        Column(
            modifier = Modifier
                .align(Alignment.Center)
                .verticalScroll(rememberScrollState())
                .padding(16.dp)
        ) {
            Text(
                text = getIntroTitleString(
                    MaterialTheme.colors.onBackground,
                    MaterialTheme.colors.primary,
                    MaterialTheme.colors.onSurface
                ), style = MaterialTheme.typography.h1
            )
            Spacer(modifier = Modifier.height(100.dp))
            IntroButton(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(44.dp)
                    .clip(MaterialTheme.shapes.small)
                    .border(1.dp, MaterialTheme.colors.onBackground, MaterialTheme.shapes.small),
                text = stringResource(id = R.string.continue_with_google),
                leadingIcon = R.drawable.ic_google_logo,
                clicked = state.isGoogleLoading
            ) {
                if (state.isGoogleLoading.not()) {
                    viewModel.updateGoogleState()
                    contract.launch(GOOGLE_LOGIN_KEY)
                }
            }
            Spacer(modifier = Modifier.height(16.dp))
            IntroButton(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(44.dp)
                    .clip(MaterialTheme.shapes.small)
                    .border(1.dp, MaterialTheme.colors.onBackground, MaterialTheme.shapes.small),
                text = stringResource(id = R.string.continue_with_email),
                leadingIcon = R.drawable.ic_at
            ) {
                viewModel.onEvent(IntroEvent.OnRegister)
            }
            Spacer(modifier = Modifier.height(24.dp))
            AlreadyHaveAccountText(
                leadingText = stringResource(id = R.string.already_have_account),
                clickableText = stringResource(id = R.string.annotated_login),
                modifier = Modifier.align(Alignment.CenterHorizontally)
            ) {
                viewModel.onEvent(IntroEvent.OnLogin)
            }
        }
    }
}

@ExperimentalMaterialApi
@ExperimentalAnimationApi
fun NavGraphBuilder.introComposable(
    onNavigate: (String) -> Unit,
    clearBackStack: () -> Unit,
) {
    composable(
        route = Screen.Intro.route,
        exitTransition = { exitTransition() },
        popExitTransition = { popExitTransition() },
        popEnterTransition = { popEnterTransition() },
        enterTransition = { enterTransition() },
    ) {
        IntroScreen(onNavigate, clearBackStack)
    }
}
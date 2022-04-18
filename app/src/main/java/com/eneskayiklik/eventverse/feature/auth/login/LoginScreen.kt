package com.eneskayiklik.eventverse.feature.auth.login

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavGraphBuilder
import com.eneskayiklik.eventverse.R
import com.eneskayiklik.eventverse.core.component.ExtendedTextField
import com.eneskayiklik.eventverse.core.component.InfoDialog
import com.eneskayiklik.eventverse.util.Screen
import com.eneskayiklik.eventverse.util.UiEvent
import com.eneskayiklik.eventverse.util.anim.ScreensAnim.enterTransition
import com.eneskayiklik.eventverse.util.anim.ScreensAnim.exitTransition
import com.eneskayiklik.eventverse.util.anim.ScreensAnim.popEnterTransition
import com.eneskayiklik.eventverse.util.anim.ScreensAnim.popExitTransition
import com.eneskayiklik.eventverse.data.event.auth.LoginEvent
import com.eneskayiklik.eventverse.feature.auth.component.IntroductionText
import com.eneskayiklik.eventverse.feature.auth.login.component.LoginButton
import com.google.accompanist.insets.statusBarsPadding
import com.google.accompanist.navigation.animation.composable
import kotlinx.coroutines.flow.collectLatest

@ExperimentalMaterialApi
@ExperimentalAnimationApi
@Composable
private fun LoginScreen(
    onNavigate: (String) -> Unit,
    clearBackStack: () -> Unit,
    viewModel: LoginViewModel = hiltViewModel()
) {
    val state = viewModel.state.collectAsState().value

    LaunchedEffect(key1 = true) {
        viewModel.event.collectLatest {
            when (it) {
                is UiEvent.ShowSnackbar -> Unit
                is UiEvent.Navigate -> {
                    onNavigate(it.id)
                }
                UiEvent.ClearBackStack -> clearBackStack()
                is UiEvent.Toast -> Unit
                else -> Unit
            }
        }
    }

    if (state.dialogState != null) InfoDialog(state = state.dialogState)

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(color = MaterialTheme.colors.background)
    ) {
        Image(
            painter = painterResource(id = R.drawable.ic_login_background),
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier.fillMaxSize()
        )
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .statusBarsPadding()
                .padding(horizontal = 16.dp),
            contentPadding = PaddingValues(bottom = 60.dp)
        ) {
            item("header_view") {
                Spacer(modifier = Modifier.height(30.dp))
                IntroductionText(
                    title = stringResource(id = R.string.sign_in_title), subtitle = stringResource(
                        id = R.string.sign_in_subtitle
                    )
                )
                Spacer(modifier = Modifier.height(20.dp))
            }
            item("email_field") {
                ExtendedTextField(
                    text = state.email.text,
                    onValueChange = {
                        viewModel.onLoginState(
                            LoginEvent.OnEmail(it)
                        )
                    },
                    error = state.email.error,
                    placeholder = stringResource(id = R.string.email_paceholder),
                    label = stringResource(id = R.string.email),
                    keyboardType = KeyboardType.Email,
                    modifier = Modifier.fillMaxWidth()
                )
            }
            item("password_field") {
                Spacer(modifier = Modifier.height(20.dp))
                ExtendedTextField(
                    text = state.password.text,
                    onValueChange = {
                        viewModel.onLoginState(
                            LoginEvent.OnPassword(it)
                        )
                    },
                    error = state.password.error,
                    placeholder = stringResource(id = R.string.password_placeholder),
                    label = stringResource(id = R.string.password),
                    keyboardType = KeyboardType.Password,
                    isPasswordVisible = state.isPasswordVisible,
                    onPasswordToggleClick = {
                        viewModel.onLoginState(
                            LoginEvent.OnTogglePassword
                        )
                    },
                    modifier = Modifier.fillMaxWidth()
                )
            }
        }
        LoginButton(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth()
                .height(44.dp)
                .align(Alignment.BottomCenter),
            clicked = state.isLoading
        ) {
            viewModel.onLoginState(LoginEvent.OnLogin)
        }
    }
}

@ExperimentalMaterialApi
@ExperimentalAnimationApi
fun NavGraphBuilder.loginComposable(
    onNavigate: (String) -> Unit,
    clearBackStack: () -> Unit,
) {
    composable(
        route = Screen.Login.route,
        exitTransition = { exitTransition() },
        popExitTransition = { popExitTransition() },
        popEnterTransition = { popEnterTransition() },
        enterTransition = { enterTransition() },
    ) {
        LoginScreen(onNavigate, clearBackStack)
    }
}
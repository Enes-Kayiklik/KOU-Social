package com.eneskayiklik.eventverse.feature_auth.presentation.login

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
import com.eneskayiklik.eventverse.core.util.Screen
import com.eneskayiklik.eventverse.core.util.UiEvent
import com.eneskayiklik.eventverse.core.util.anim.ScreensAnim.enterTransition
import com.eneskayiklik.eventverse.core.util.anim.ScreensAnim.exitTransition
import com.eneskayiklik.eventverse.core.util.anim.ScreensAnim.popEnterTransition
import com.eneskayiklik.eventverse.core.util.anim.ScreensAnim.popExitTransition
import com.eneskayiklik.eventverse.feature_auth.data.event.LoginEvent
import com.eneskayiklik.eventverse.feature_auth.presentation.component.IntroductionText
import com.eneskayiklik.eventverse.feature_auth.presentation.login.component.LoginButton
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
            /*item("forget_password") {
                Text(
                    text = stringResource(id = R.string.forget_password),
                    style = MaterialTheme.typography.caption,
                    textAlign = TextAlign.End,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 8.dp, bottom = 8.dp),
                )
            }
            item("login_button") {
                LoginButton(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(50.dp),
                    clicked = loginButton
                ) {
                    viewModel.onLoginState(LoginState.OnLogin)
                }
            }
            item("or") {
                Spacer(modifier = Modifier.height(16.dp))
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceAround
                ) {
                    Spacer(
                        modifier = Modifier
                            .height(1.dp)
                            .alpha(0.5F)
                            .weight(1F)
                            .padding(end = 4.dp)
                            .background(color = MaterialTheme.colors.onSurface)
                    )
                    Text(
                        text = stringResource(id = R.string.or),
                        style = MaterialTheme.typography.caption
                    )
                    Spacer(
                        modifier = Modifier
                            .height(1.dp)
                            .alpha(0.5F)
                            .weight(1F)
                            .padding(start = 4.dp)
                            .background(color = MaterialTheme.colors.onSurface)
                    )
                }
                Spacer(modifier = Modifier.height(16.dp))
            }
            item("google_button") {
                GoogleButton(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(50.dp),
                    clicked = googleButton
                ) {
                    viewModel.onLoginState(LoginState.OnGoogle)
                }
            }
            item("signup_button") {
                TextButton(
                    onClick = { viewModel.onLoginState(LoginState.OnNavigateRegister) },
                    Modifier
                        .fillMaxWidth()
                        .height(50.dp)
                ) {
                    Text(
                        text = stringResource(id = R.string.signup_button),
                        style = MaterialTheme.typography.h1
                    )
                }
            }*/
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
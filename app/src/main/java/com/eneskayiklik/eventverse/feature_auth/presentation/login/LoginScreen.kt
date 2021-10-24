package com.eneskayiklik.eventverse.feature_auth.presentation.login

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Email
import androidx.compose.material.icons.rounded.Lock
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavGraphBuilder
import com.eneskayiklik.eventverse.R
import com.eneskayiklik.eventverse.core.component.ExtendedTextField
import com.eneskayiklik.eventverse.core.util.Screen
import com.eneskayiklik.eventverse.core.util.UiEvent
import com.eneskayiklik.eventverse.feature_auth.presentation.login.component.GoogleButton
import com.eneskayiklik.eventverse.feature_auth.presentation.login.component.LoginButton
import com.eneskayiklik.eventverse.feature_auth.presentation.login.util.LoginState
import com.google.accompanist.navigation.animation.composable
import kotlinx.coroutines.flow.collectLatest

@ExperimentalAnimationApi
@Composable
private fun LoginScreen(
    onNavigate: (String) -> Unit,
    clearBackStack: () -> Unit,
    viewModel: LoginViewModel = hiltViewModel()
) {
    val email = viewModel.emailState.collectAsState().value
    val password = viewModel.passwordState.collectAsState().value
    val googleButton = viewModel.googleButtonState.collectAsState().value
    val loginButton = viewModel.loginButtonState.collectAsState().value

    LaunchedEffect(key1 = true) {
        viewModel.uiState.collectLatest {
            when (it) {
                is UiEvent.Navigate -> {
                    when (it.id) {
                        Screen.Timeline.route -> clearBackStack()
                    }
                    onNavigate(it.id)
                }
                UiEvent.CleatBackStack -> clearBackStack()
            }
        }
    }

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .background(color = MaterialTheme.colors.background)
            .padding(start = 16.dp, end = 16.dp)
    ) {
        item("header_view") {
            Spacer(modifier = Modifier.height(100.dp))
            Text(
                text = stringResource(id = R.string.app_name),
                style = MaterialTheme.typography.h1.copy(
                    fontSize = 36.sp,
                    color = MaterialTheme.colors.primary
                ),
                textAlign = TextAlign.Center, modifier = Modifier.fillMaxWidth(),
            )
        }
        item("login_text") {
            Spacer(modifier = Modifier.height(50.dp))
            Text(
                text = stringResource(id = R.string.login),
                style = MaterialTheme.typography.h1,
                textAlign = TextAlign.Start, modifier = Modifier.fillMaxWidth(),
            )
            Spacer(modifier = Modifier.height(4.dp))
        }
        item("email_field") {
            ExtendedTextField(
                text = email.text,
                onValueChange = {
                    viewModel.onLoginState(
                        LoginState.OnEmail(it)
                    )
                },
                error = email.error,
                leadingIcon = Icons.Rounded.Email,
                label = stringResource(id = R.string.email),
                keyboardType = KeyboardType.Email,
                modifier = Modifier.fillMaxWidth()
            )
        }
        item("password_field") {
            Spacer(modifier = Modifier.height(8.dp))
            ExtendedTextField(
                text = password.text,
                onValueChange = {
                    viewModel.onLoginState(
                        LoginState.OnPassword(it)
                    )
                },
                error = password.error,
                leadingIcon = Icons.Rounded.Lock,
                label = stringResource(id = R.string.password),
                keyboardType = KeyboardType.Password,
                isPasswordVisible = password.isPasswordShowing,
                onPasswordToggleClick = {
                    viewModel.onLoginState(
                        LoginState.OnTogglePassword
                    )
                },
                modifier = Modifier.fillMaxWidth()
            )
        }
        item("forget_password") {
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
            Spacer(modifier = Modifier.height(8.dp))
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
        }
    }
}

@ExperimentalAnimationApi
fun NavGraphBuilder.loginComposable(
    onNavigate: (String) -> Unit,
    clearBackStack: () -> Unit,
) {
    composable(
        route = Screen.Login.route,
    ) {
        LoginScreen(onNavigate, clearBackStack)
    }
}
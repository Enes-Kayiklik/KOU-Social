package com.eneskayiklik.eventverse.feature_auth.presentation.signup

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Email
import androidx.compose.material.icons.rounded.Password
import androidx.compose.material.icons.rounded.Person
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
import com.eneskayiklik.eventverse.core.util.anim.ScreensAnim.enterTransition
import com.eneskayiklik.eventverse.core.util.anim.ScreensAnim.exitTransition
import com.eneskayiklik.eventverse.core.util.anim.ScreensAnim.popEnterTransition
import com.eneskayiklik.eventverse.core.util.anim.ScreensAnim.popExitTransition
import com.eneskayiklik.eventverse.feature_auth.data.event.SignupEvent
import com.eneskayiklik.eventverse.feature_auth.presentation.login.component.GoogleButton
import com.eneskayiklik.eventverse.feature_auth.presentation.login.component.LoginButton
import com.eneskayiklik.eventverse.feature_auth.presentation.signup.components.FacultySelection
import com.google.accompanist.navigation.animation.composable
import kotlinx.coroutines.flow.collectLatest

@ExperimentalMaterialApi
@ExperimentalAnimationApi
@Composable
private fun SignupScreen(
    onNavigate: (String) -> Unit,
    clearBackStack: () -> Unit,
    viewModel: SignupViewModel = hiltViewModel()
) {
    val state = viewModel.state.collectAsState().value

    LaunchedEffect(key1 = true) {
        viewModel.event.collectLatest {
            when (it) {
                is UiEvent.ShowSnackbar -> {
                }
                is UiEvent.Navigate -> {
                    when (it.id) {
                        Screen.Explore.route -> clearBackStack()
                    }
                    onNavigate(it.id)
                }
                UiEvent.ClearBackStack -> clearBackStack()
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
                text = stringResource(id = R.string.register_button),
                style = MaterialTheme.typography.h1,
                textAlign = TextAlign.Start, modifier = Modifier.fillMaxWidth(),
            )
            Spacer(modifier = Modifier.height(4.dp))
        }
        item("fullname_field") {
            ExtendedTextField(
                text = state.fullName.text,
                onValueChange = {
                    viewModel.onSignupEvent(
                        SignupEvent.OnFullName(it)
                    )
                },
                error = state.fullName.error,
                leadingIcon = Icons.Rounded.Person,
                label = stringResource(id = R.string.fullname),
                keyboardType = KeyboardType.Email,
                modifier = Modifier.fillMaxWidth()
            )
        }
        item("email_field") {
            Spacer(modifier = Modifier.height(8.dp))
            ExtendedTextField(
                text = state.email.text,
                onValueChange = {
                    viewModel.onSignupEvent(
                        SignupEvent.OnEmail(it)
                    )
                },
                error = state.email.error,
                leadingIcon = Icons.Rounded.Email,
                label = stringResource(id = R.string.email),
                keyboardType = KeyboardType.Email,
                modifier = Modifier.fillMaxWidth()
            )
        }
        item("password_field") {
            Spacer(modifier = Modifier.height(8.dp))
            ExtendedTextField(
                text = state.password.text,
                onValueChange = {
                    viewModel.onSignupEvent(
                        SignupEvent.OnPassword(it)
                    )
                },
                error = state.password.error,
                leadingIcon = Icons.Rounded.Password,
                label = stringResource(id = R.string.password),
                keyboardType = KeyboardType.Password,
                isPasswordVisible = state.isPasswordVisible,
                onPasswordToggleClick = {
                    viewModel.onSignupEvent(
                        SignupEvent.OnTogglePassword
                    )
                },
                modifier = Modifier.fillMaxWidth()
            )
        }
        item("faculty") {
            Spacer(modifier = Modifier.height(16.dp))
            FacultySelection(
                state.selectedDepartment,
                state.faculties,
                state.isFacultyPopupActive,
                viewModel::onSignupEvent
            )
        }
        item("signup_button") {
            Spacer(modifier = Modifier.height(16.dp))
            LoginButton(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp),
                text = stringResource(id = R.string.register_button),
                clicked = false
            ) {
                viewModel.onSignupEvent(SignupEvent.OnRegister)
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
                clicked = false
            ) {
                viewModel.onSignupEvent(SignupEvent.OnGoogle)
            }
        }
        item("login_button") {
            Spacer(modifier = Modifier.height(8.dp))
            TextButton(
                onClick = { clearBackStack() },
                Modifier
                    .fillMaxWidth()
                    .height(50.dp)
            ) {
                Text(
                    text = stringResource(id = R.string.login_button),
                    style = MaterialTheme.typography.h1
                )
            }
        }
    }
}

@ExperimentalMaterialApi
@ExperimentalAnimationApi
fun NavGraphBuilder.signupComposable(
    onNavigate: (String) -> Unit,
    clearBackStack: () -> Unit,
) {
    composable(
        route = Screen.Signup.route,
        exitTransition = { exitTransition() },
        popExitTransition = { popExitTransition() },
        popEnterTransition = { popEnterTransition() },
        enterTransition = { enterTransition() },
    ) {
        SignupScreen(onNavigate, clearBackStack)
    }
}
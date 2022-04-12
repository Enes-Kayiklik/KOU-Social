package com.eneskayiklik.eventverse.feature_settings.presentation.update_password

import androidx.compose.runtime.Composable
import android.widget.Toast
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
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
import com.eneskayiklik.eventverse.feature_auth.presentation.login.component.LoginButton
import com.eneskayiklik.eventverse.feature_settings.data.event.UpdatePasswordEvent
import com.eneskayiklik.eventverse.feature_settings.presentation.update_password.component.UpdatePasswordToolbar
import com.google.accompanist.insets.statusBarsPadding
import com.google.accompanist.navigation.animation.composable
import kotlinx.coroutines.flow.collectLatest

@ExperimentalMaterialApi
@ExperimentalAnimationApi
@Composable
private fun UpdatePasswordScreen(
    onNavigate: (String) -> Unit,
    clearBackStack: () -> Unit,
    viewModel: UpdatePasswordViewModel = hiltViewModel()
) {
    val state = viewModel.state.collectAsState().value
    val context = LocalContext.current
    LaunchedEffect(key1 = true) {
        viewModel.event.collectLatest {
            when (it) {
                is UiEvent.Toast -> {
                    Toast.makeText(context, it.message, Toast.LENGTH_LONG).show()
                }
                is UiEvent.Navigate -> onNavigate(it.id)
                UiEvent.ClearBackStack -> clearBackStack()
                else -> Unit
            }
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(color = MaterialTheme.colors.background)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colors.background)
        ) {
            UpdatePasswordToolbar(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(MaterialTheme.colors.background)
                    .statusBarsPadding()
                    .height(56.dp),
                onStartIconClick = clearBackStack
            )
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 16.dp),
                contentPadding = PaddingValues(bottom = 60.dp)
            ) {
                item("email_field") {
                    Spacer(modifier = Modifier.height(20.dp))
                    ExtendedTextField(
                        text = state.email.text,
                        onValueChange = {
                            viewModel.onEvent(
                                UpdatePasswordEvent.OnEmail(it)
                            )
                        },
                        error = state.email.error,
                        placeholder = stringResource(id = R.string.email_paceholder),
                        label = stringResource(id = R.string.email),
                        keyboardType = KeyboardType.Email,
                        modifier = Modifier.fillMaxWidth()
                    )
                }
                item("old_password") {
                    Spacer(modifier = Modifier.height(20.dp))
                    ExtendedTextField(
                        text = state.password.text,
                        onValueChange = {
                            viewModel.onEvent(
                                UpdatePasswordEvent.OnPassword(it)
                            )
                        },
                        error = state.password.error,
                        placeholder = stringResource(id = R.string.old_password_placeholder),
                        label = stringResource(id = R.string.old_password),
                        keyboardType = KeyboardType.Password,
                        isPasswordVisible = state.isPasswordVisible,
                        onPasswordToggleClick = {
                            viewModel.onEvent(
                                UpdatePasswordEvent.OnTogglePassword
                            )
                        },
                        modifier = Modifier.fillMaxWidth()
                    )
                }
                item("new_password") {
                    Spacer(modifier = Modifier.height(20.dp))
                    ExtendedTextField(
                        text = state.newPassword.text,
                        onValueChange = {
                            viewModel.onEvent(
                                UpdatePasswordEvent.OnNewPassword(it)
                            )
                        },
                        error = state.newPassword.error,
                        placeholder = stringResource(id = R.string.password_placeholder),
                        label = stringResource(id = R.string.new_password),
                        keyboardType = KeyboardType.Password,
                        isPasswordVisible = state.isNewPasswordVisible,
                        onPasswordToggleClick = {
                            viewModel.onEvent(
                                UpdatePasswordEvent.OnToggleNewPassword
                            )
                        },
                        modifier = Modifier.fillMaxWidth()
                    )
                }
            }
        }

        LoginButton(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth()
                .height(44.dp)
                .align(Alignment.BottomCenter),
            clicked = state.isLoading,
            text = stringResource(id = R.string.update_password)
        ) {
            viewModel.onEvent(UpdatePasswordEvent.OnReset)
        }
    }
}

@ExperimentalMaterialApi
@ExperimentalAnimationApi
fun NavGraphBuilder.updatePasswordComposable(
    onNavigate: (String) -> Unit,
    clearBackStack: () -> Unit,
) {
    composable(
        route = Screen.UpdatePassword.route,
        exitTransition = { exitTransition() },
        popExitTransition = { popExitTransition() },
        popEnterTransition = { popEnterTransition() },
        enterTransition = { enterTransition() },
    ) {
        UpdatePasswordScreen(onNavigate, clearBackStack)
    }
}
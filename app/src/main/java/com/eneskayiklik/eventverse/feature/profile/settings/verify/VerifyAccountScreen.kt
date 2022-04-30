package com.eneskayiklik.eventverse.feature.profile.settings.verify

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
import com.eneskayiklik.eventverse.util.Screen
import com.eneskayiklik.eventverse.util.UiEvent
import com.eneskayiklik.eventverse.util.anim.ScreensAnim.enterTransition
import com.eneskayiklik.eventverse.util.anim.ScreensAnim.exitTransition
import com.eneskayiklik.eventverse.util.anim.ScreensAnim.popEnterTransition
import com.eneskayiklik.eventverse.util.anim.ScreensAnim.popExitTransition
import com.eneskayiklik.eventverse.feature.auth.login.component.LoginButton
import com.eneskayiklik.eventverse.data.event.profile.VerifyAccountEvent
import com.eneskayiklik.eventverse.feature.profile.settings.verify.component.VerifyAccountToolbar
import com.google.accompanist.insets.statusBarsPadding
import com.google.accompanist.navigation.animation.composable
import kotlinx.coroutines.flow.collectLatest

@ExperimentalMaterialApi
@ExperimentalAnimationApi
@Composable
private fun VerifyAccountScreen(
    onNavigate: (String) -> Unit,
    clearBackStack: () -> Unit,
    viewModel: VerifyAccountViewModel = hiltViewModel()
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
            VerifyAccountToolbar(
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
                        text = state.verifyCode.text,
                        onValueChange = {
                            viewModel.onEvent(
                                VerifyAccountEvent.OnVerifyCode(it)
                            )
                        },
                        error = state.verifyCode.error,
                        placeholder = stringResource(id = R.string.verification_code_placeholder),
                        label = stringResource(id = R.string.verification_code),
                        keyboardType = KeyboardType.Email,
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
            text = stringResource(id = R.string.verify_account)
        ) {
            viewModel.onEvent(VerifyAccountEvent.OnVerifyAccount)
        }
    }
}

@ExperimentalMaterialApi
@ExperimentalAnimationApi
fun NavGraphBuilder.verifyAccountComposable(
    onNavigate: (String) -> Unit,
    clearBackStack: () -> Unit,
) {
    composable(
        route = Screen.Verify.route,
        exitTransition = { exitTransition() },
        popExitTransition = { popExitTransition() },
        popEnterTransition = { popEnterTransition() },
        enterTransition = { enterTransition() },
    ) {
        VerifyAccountScreen(onNavigate, clearBackStack)
    }
}
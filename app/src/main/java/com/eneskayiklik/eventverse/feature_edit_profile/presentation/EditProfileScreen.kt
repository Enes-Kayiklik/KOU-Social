package com.eneskayiklik.eventverse.feature_edit_profile.presentation

import android.app.Activity
import android.content.Intent
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavGraphBuilder
import com.eneskayiklik.eventverse.R
import com.eneskayiklik.eventverse.core.presentation.MainActivity
import com.eneskayiklik.eventverse.core.util.Screen
import com.eneskayiklik.eventverse.core.util.UiEvent
import com.eneskayiklik.eventverse.core.util.anim.ScreensAnim.enterTransition
import com.eneskayiklik.eventverse.core.util.anim.ScreensAnim.exitTransition
import com.eneskayiklik.eventverse.core.util.anim.ScreensAnim.popEnterTransition
import com.eneskayiklik.eventverse.core.util.anim.ScreensAnim.popExitTransition
import com.eneskayiklik.eventverse.feature_auth.presentation.login.component.LoginButton
import com.eneskayiklik.eventverse.feature_edit_profile.presentation.component.*
import com.google.accompanist.insets.statusBarsPadding
import com.google.accompanist.navigation.animation.composable
import com.eneskayiklik.eventverse.feature_settings.presentation.component.sectionTitle
import kotlinx.coroutines.flow.collectLatest

@OptIn(
    ExperimentalMaterialApi::class,
    ExperimentalFoundationApi::class,
    ExperimentalAnimationApi::class
)
@Composable
private fun SettingsScreen(
    onNavigate: (String) -> Unit,
    clearBackStack: () -> Unit,
    viewModel: EditProfileViewModel = hiltViewModel()
) {
    val state = viewModel.state.collectAsState().value
    val user = state.user
    val context = LocalContext.current
    val accountTitle = stringResource(id = R.string.personal)
    val socialTitle = stringResource(id = R.string.social_accounts)

    LaunchedEffect(key1 = true) {
        viewModel.event.collectLatest {
            when (it) {
                is UiEvent.RestartApp -> {
                    context.startActivity(Intent(context, MainActivity::class.java))
                    (context as? Activity)?.finish()
                }
                is UiEvent.Navigate -> onNavigate(it.id)
                UiEvent.ClearBackStack -> clearBackStack()
                else -> Unit
            }
        }
    }

    Box(modifier = Modifier.fillMaxSize()) {
        if (state.isLoading) CircularProgressIndicator(
            modifier = Modifier
                .size(32.dp)
                .align(Alignment.Center),
            color = MaterialTheme.colors.primary,
            strokeWidth = 2.dp
        )

        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colors.background)
        ) {
            EditProfileToolbar(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(MaterialTheme.colors.background)
                    .statusBarsPadding()
                    .height(56.dp),
                onStartIconClick = clearBackStack
            )
            LazyColumn(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1F),
                contentPadding = PaddingValues(top = 20.dp, bottom = 70.dp),
                verticalArrangement = Arrangement.spacedBy(40.dp)
            ) {
                sectionTitle(
                    accountTitle, modifier = Modifier.padding(
                        horizontal = 32.dp, vertical = 8.dp
                    )
                )
                photoSection(user.profilePic, viewModel::onEvent)
                nameSection(user.fullName, viewModel::onEvent)
                ageSection(state.formattedBirthdate, viewModel::onEvent)
                departmentSection(user.department, state.isDepartmentPopupVisible, viewModel::onEvent)
                sectionTitle(
                    socialTitle, modifier = Modifier.padding(
                        horizontal = 32.dp, vertical = 8.dp
                    )
                )
                socialSection(user.socialAccounts, viewModel::onEvent)
            }
        }

        LoginButton(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth()
                .height(44.dp)
                .align(Alignment.BottomCenter),
            clicked = state.isLoading,
            text = stringResource(id = R.string.update_profile)
        ) {
            // viewModel.onLoginState(LoginEvent.OnLogin)
        }
    }
}

@ExperimentalAnimationApi
fun NavGraphBuilder.editProfileComposable(
    onNavigate: (String) -> Unit,
    clearBackStack: () -> Unit
) {
    composable(
        route = Screen.EditProfile.route,
        exitTransition = { exitTransition() },
        popExitTransition = { popExitTransition() },
        popEnterTransition = { popEnterTransition() },
        enterTransition = { enterTransition() },
    ) {
        SettingsScreen(onNavigate, clearBackStack)
    }
}
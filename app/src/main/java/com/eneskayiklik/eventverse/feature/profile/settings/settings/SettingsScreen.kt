package com.eneskayiklik.eventverse.feature.profile.settings.settings

import android.app.Activity
import android.content.Intent
import androidx.activity.compose.BackHandler
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Divider
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.navigation.NavGraphBuilder
import com.eneskayiklik.eventverse.core.presentation.MainActivity
import com.eneskayiklik.eventverse.util.Screen
import com.eneskayiklik.eventverse.util.UiEvent
import com.eneskayiklik.eventverse.util.anim.ScreensAnim.enterTransition
import com.eneskayiklik.eventverse.util.anim.ScreensAnim.exitTransition
import com.eneskayiklik.eventverse.util.anim.ScreensAnim.popEnterTransition
import com.eneskayiklik.eventverse.util.anim.ScreensAnim.popExitTransition
import com.google.accompanist.insets.statusBarsPadding
import com.google.accompanist.navigation.animation.composable
import kotlinx.coroutines.flow.collectLatest
import com.eneskayiklik.eventverse.R
import com.eneskayiklik.eventverse.core.component.InfoDialog
import com.eneskayiklik.eventverse.data.model.profile.Language
import com.eneskayiklik.eventverse.util.GOOGLE_LOGIN_KEY
import com.eneskayiklik.eventverse.util.contract.GoogleLoginContract
import com.eneskayiklik.eventverse.util.extension.shareAppLink
import com.eneskayiklik.eventverse.feature.profile.settings.settings.component.*
import com.google.android.gms.common.api.ApiException

@OptIn(
    ExperimentalMaterialApi::class,
    ExperimentalFoundationApi::class,
    ExperimentalAnimationApi::class
)
@Composable
private fun SettingsScreen(
    onNavigate: (String) -> Unit,
    clearBackStack: () -> Unit,
    viewModel: SettingsViewModel = hiltViewModel()
) {
    val state = viewModel.state.collectAsState().value
    val user = state.user
    val context = LocalContext.current
    val accountTitle = stringResource(id = R.string.account)
    val settingsTitle = stringResource(id = R.string.settings)
    val isDarkModeEnabled = viewModel.isDarkModeEnabled.collectAsState(false).value
    val activeLanguage = viewModel.activeLanguage.collectAsState(Language.UNDEFINED).value

    val lifecycleOwner = LocalLifecycleOwner.current

    val contract = rememberLauncherForActivityResult(contract = GoogleLoginContract()) { task ->
        try {
            val account = task?.getResult(ApiException::class.java)
            viewModel.deleteAccountPopup(account ?: return@rememberLauncherForActivityResult)
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    DisposableEffect(lifecycleOwner) {
        val observer = LifecycleEventObserver { _, event ->
            if (event == Lifecycle.Event.ON_RESUME) {
                viewModel.updateCurrentUser()
            }
        }

        // Add the observer to the lifecycle
        lifecycleOwner.lifecycle.addObserver(observer)

        // When the effect leaves the Composition, remove the observer
        onDispose {
            lifecycleOwner.lifecycle.removeObserver(observer)
        }
    }

    BackHandler(state.isLoading) { }

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

    if (state.errorDialogState != null) InfoDialog(state = state.errorDialogState)

    Box(modifier = Modifier.fillMaxSize()) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colors.background)
        ) {
            SettingsToolbar(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(MaterialTheme.colors.background)
                    .statusBarsPadding()
                    .height(56.dp),
                onStartIconClick = {
                    if (state.isLoading.not()) clearBackStack()
                }
            )
            LazyColumn(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1F),
                contentPadding = PaddingValues(vertical = 20.dp)
            ) {
                editProfileButton(user) {
                    onNavigate(Screen.EditProfile.route)
                }
                sectionTitle(
                    accountTitle, modifier = Modifier.padding(
                        horizontal = 32.dp, vertical = 8.dp
                    )
                )
                verifyAccountButton(user.verified) {
                    if (user.verified.not()) onNavigate(Screen.Verify.route)
                }
                if (user.socialLogin.not()) updatePasswordButton {
                    onNavigate(Screen.UpdatePassword.route)
                }
                deleteAccountButton(user.socialLogin) {
                    // We need to re auth user. see: https://firebase.google.com/docs/auth/android/manage-users#delete_a_user
                    if (user.socialLogin) contract.launch(GOOGLE_LOGIN_KEY)
                    else onNavigate(Screen.DeleteAccount.route)
                }
                sectionTitle(
                    settingsTitle,
                    modifier = Modifier.padding(start = 32.dp, end = 32.dp, top = 16.dp)
                )
                languageButton(activeLanguage, viewModel::updateLanguage)
                darkModeButton(isDarkModeEnabled, viewModel::toggleTheme)
                item {
                    Divider(
                        modifier = Modifier
                            .height(1.dp)
                            .background(MaterialTheme.colors.secondary)
                    )
                }

                inviteFriendButton {
                    context.shareAppLink()
                }
                logoutButton(viewModel::logOut)
            }
        }
        if (state.isLoading) CircularProgressIndicator(
            modifier = Modifier
                .size(32.dp)
                .align(Alignment.Center),
            color = MaterialTheme.colors.primary,
            strokeWidth = 2.dp
        )
    }
}

@ExperimentalAnimationApi
fun NavGraphBuilder.settingsComposable(
    onNavigate: (String) -> Unit,
    clearBackStack: () -> Unit,
) {
    composable(
        route = Screen.SettingsScreen.route,
        exitTransition = { exitTransition() },
        popExitTransition = { popExitTransition() },
        popEnterTransition = { popEnterTransition() },
        enterTransition = { enterTransition() },
    ) {
        SettingsScreen(onNavigate, clearBackStack)
    }
}
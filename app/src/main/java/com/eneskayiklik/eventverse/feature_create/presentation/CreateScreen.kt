package com.eneskayiklik.eventverse.feature_create.presentation

import androidx.compose.animation.*
import androidx.compose.animation.core.tween
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.MaterialTheme
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavGraphBuilder
import com.eneskayiklik.eventverse.R
import com.eneskayiklik.eventverse.core.util.Screen
import com.eneskayiklik.eventverse.feature_auth.presentation.login.component.LoginButton
import com.eneskayiklik.eventverse.feature_create.presentation.component.lazy_section.aboutEventSection
import com.eneskayiklik.eventverse.feature_create.presentation.component.lazy_section.dateTimeSection
import com.eneskayiklik.eventverse.feature_create.presentation.component.lazy_section.eventPhotoSection
import com.eneskayiklik.eventverse.feature_create.presentation.component.lazy_section.locationSection
import com.eneskayiklik.eventverse.feature_explore.presentation.component.EventverseAppbar
import com.google.accompanist.navigation.animation.composable

@ExperimentalMaterialApi
@ExperimentalFoundationApi
@ExperimentalAnimationApi
@Composable
private fun CreateScreen(
    onNavigate: (String) -> Unit,
    clearBackStack: () -> Unit,
    viewModel: CreateViewModel = hiltViewModel()
) {
    val listState = rememberLazyListState()
    val state = viewModel.state.collectAsState().value

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colors.background),
        contentAlignment = Alignment.Center
    ) {
        Column(
            modifier = Modifier.fillMaxSize()
        ) {
            EventverseAppbar(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(MaterialTheme.colors.primary)
                    .padding(top = 20.dp)
                    .height(60.dp),
                title = stringResource(id = R.string.create_event),
                startIcon = Icons.Rounded.ArrowBack,
                onStartIconClick = clearBackStack,
            )

            LazyColumn(
                state = listState
            ) {
                aboutEventSection(state.title, state.description, viewModel)
                item { Spacer(modifier = Modifier.height(10.dp)) }
                eventPhotoSection(state.coverImage, viewModel)
                item { Spacer(modifier = Modifier.height(10.dp)) }
                dateTimeSection(state, viewModel)
                item { Spacer(modifier = Modifier.height(10.dp)) }
                locationSection()
                item { Spacer(modifier = Modifier.height(10.dp)) }
            }
        }
        AnimatedVisibility(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(horizontal = 12.dp),
            visible = listState.firstVisibleItemIndex == 0,
            enter = expandHorizontally(),
            exit = shrinkHorizontally()
        ) {
            LoginButton(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 4.dp, end = 4.dp, bottom = 16.dp)
                    .height(50.dp),
                text = stringResource(id = R.string.create_event),
                clicked = false
            ) {

            }
        }
    }
}

@ExperimentalMaterialApi
@ExperimentalFoundationApi
@ExperimentalAnimationApi
fun NavGraphBuilder.createComposable(
    onNavigate: (String) -> Unit,
    clearBackStack: () -> Unit,
) {
    composable(
        route = Screen.CreateEvent.route,
        enterTransition = { _, _ ->
            slideInVertically(
                initialOffsetY = { fullWidth -> fullWidth },
                animationSpec = tween(durationMillis = 300)
            )
        },
        popExitTransition = { _, _ ->
            slideOutVertically(
                targetOffsetY = { fullWidth -> fullWidth },
                animationSpec = tween(durationMillis = 300)
            )
        }
    ) {
        CreateScreen(onNavigate, clearBackStack)
    }
}
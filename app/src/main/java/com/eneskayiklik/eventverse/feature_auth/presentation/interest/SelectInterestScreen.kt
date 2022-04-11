package com.eneskayiklik.eventverse.feature_auth.presentation.interest

import androidx.compose.animation.*
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.*
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.MaterialTheme
import androidx.compose.material.ScaffoldState
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavGraphBuilder
import com.eneskayiklik.eventverse.R
import com.eneskayiklik.eventverse.core.util.Screen
import com.eneskayiklik.eventverse.core.util.UiEvent
import com.eneskayiklik.eventverse.core.util.anim.ScreensAnim.enterTransition
import com.eneskayiklik.eventverse.core.util.anim.ScreensAnim.exitTransition
import com.eneskayiklik.eventverse.core.util.anim.ScreensAnim.popEnterTransition
import com.eneskayiklik.eventverse.core.util.anim.ScreensAnim.popExitTransition
import com.eneskayiklik.eventverse.feature_auth.domain.model.InterestModel
import com.eneskayiklik.eventverse.feature_auth.presentation.interest.component.SingleInterestModel
import com.eneskayiklik.eventverse.feature_auth.presentation.interest.util.SelectInterestEvent
import com.eneskayiklik.eventverse.feature_auth.presentation.login.component.LoginButton
import com.google.accompanist.insets.statusBarsPadding
import com.google.accompanist.navigation.animation.composable
import kotlinx.coroutines.flow.collectLatest

@ExperimentalAnimationApi
@ExperimentalMaterialApi
@ExperimentalFoundationApi
@Composable
private fun SelectInterestScreen(
    onNavigate: (String) -> Unit,
    clearBackStack: () -> Unit,
    scaffoldState: ScaffoldState,
    viewModel: SelectInterestViewModel = hiltViewModel()
) {
    val interestList = viewModel.interests.collectAsState().value
    val selectButton = viewModel.isSelectClicked.collectAsState().value
    val listState = rememberLazyListState()
    val okText = stringResource(id = R.string.ok)

    LaunchedEffect(key1 = true) {
        viewModel.uiState.collectLatest {
            when (it) {
                is UiEvent.ShowSnackbar -> {
                    scaffoldState.snackbarHostState.showSnackbar(
                        message = it.message,
                        actionLabel = okText
                    )
                }
                is UiEvent.Navigate -> {
                    when (it.id) {
                        Screen.Home.route -> clearBackStack()
                    }
                    onNavigate(it.id)
                }
                UiEvent.ClearBackStack -> clearBackStack()
            }
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(color = MaterialTheme.colors.background)
            .statusBarsPadding()
            .padding(start = 12.dp, end = 12.dp, top = 16.dp)
    ) {
        Column {
            Text(
                text = stringResource(id = R.string.interest_title),
                style = MaterialTheme.typography.h1.copy(fontSize = 32.sp),
                modifier = Modifier.padding(start = 4.dp)
            )
            Text(
                text = stringResource(id = R.string.interest_desc),
                style = MaterialTheme.typography.subtitle1,
                modifier = Modifier.padding(start = 4.dp)
            )
            Spacer(modifier = Modifier.height(16.dp))
            InterestGrid(interestList, state = listState, onSelect = viewModel::onSelectInterest)
        }
        AnimatedVisibility(
            modifier = Modifier.align(Alignment.BottomCenter),
            visible = listState.firstVisibleItemIndex == 0,
            enter = expandHorizontally(),
            exit = shrinkHorizontally()
        ) {
            LoginButton(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 4.dp, end = 4.dp, bottom = 16.dp)
                    .height(50.dp),
                text = stringResource(id = R.string.select_and_go),
                clicked = selectButton
            ) {
                viewModel.onEvent(SelectInterestEvent.OnSelectInterest)
            }
        }
    }
}

@ExperimentalMaterialApi
@ExperimentalFoundationApi
@Composable
private fun InterestGrid(
    interests: List<InterestModel>,
    state: LazyListState = rememberLazyListState(),
    onSelect: (id: Int) -> Unit
) {
   /* LazyVerticalGrid(
        state = state,
        cells = GridCells.Fixed(3)
    ) {
        items(interests) { item ->
            SingleInterestModel(item, onSelect)
        }
    }*/
}

@ExperimentalMaterialApi
@ExperimentalFoundationApi
@ExperimentalAnimationApi
fun NavGraphBuilder.selectInterestComposable(
    onNavigate: (String) -> Unit,
    clearBackStack: () -> Unit,
    scaffoldState: ScaffoldState
) {
    composable(
        route = Screen.SelectInterest.route,
        exitTransition = { exitTransition() },
        popExitTransition = { popExitTransition() },
        popEnterTransition = { popEnterTransition() },
        enterTransition = { enterTransition() },
    ) {
        SelectInterestScreen(onNavigate, clearBackStack, scaffoldState)
    }
}
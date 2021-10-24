package com.eneskayiklik.eventverse.feature_auth.presentation.interest

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.animation.slideInHorizontally
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.*
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavGraphBuilder
import com.eneskayiklik.eventverse.core.util.Screen
import com.eneskayiklik.eventverse.core.util.UiEvent
import com.eneskayiklik.eventverse.feature_auth.domain.model.InterestModel
import com.eneskayiklik.eventverse.feature_auth.presentation.interest.component.SingleInterestModel
import com.eneskayiklik.eventverse.feature_auth.presentation.interest.util.SelectInterestEvent
import com.eneskayiklik.eventverse.feature_auth.presentation.login.component.LoginButton
import com.google.accompanist.navigation.animation.composable
import kotlinx.coroutines.flow.collectLatest

@ExperimentalAnimationApi
@ExperimentalMaterialApi
@ExperimentalFoundationApi
@Composable
private fun SelectInterestScreen(
    onNavigate: (String) -> Unit,
    clearBackStack: () -> Unit,
    viewModel: SelectInterestViewModel = hiltViewModel()
) {
    val interestList = viewModel.interests.collectAsState().value
    val selectButton = viewModel.isSelectClicked.collectAsState().value
    val listState = rememberLazyListState()
    val buttonScale by animateFloatAsState(targetValue = if (listState.firstVisibleItemIndex == 0) 1F else 0F)

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

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(color = MaterialTheme.colors.background)
            .padding(start = 12.dp, end = 12.dp)
    ) {
        Column {
            Spacer(modifier = Modifier.height(48.dp))
            Text(
                text = "Interests",
                style = MaterialTheme.typography.h1.copy(fontSize = 32.sp),
                modifier = Modifier.padding(start = 4.dp)
            )
            Text(
                text = "Lorem ipsum dolor sit amet elit.",
                style = MaterialTheme.typography.subtitle1,
                modifier = Modifier.padding(start = 4.dp)
            )
            Spacer(modifier = Modifier.height(16.dp))
            InterestGrid(interestList, state = listState, onSelect = viewModel::onSelectInterest)
        }
        LoginButton(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .fillMaxWidth()
                .padding(start = 4.dp, end = 4.dp, bottom = 16.dp)
                .height(50.dp)
                .scale(buttonScale),
            text = "Select & Go",
            clicked = selectButton
        ) {
            viewModel.onEvent(SelectInterestEvent.OnSelectInterest)
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
    LazyVerticalGrid(
        state = state,
        cells = GridCells.Fixed(3)
    ) {
        items(interests) { item ->
            SingleInterestModel(item, onSelect)
        }
    }
}

@ExperimentalMaterialApi
@ExperimentalFoundationApi
@ExperimentalAnimationApi
fun NavGraphBuilder.selectInterestComposable(
    onNavigate: (String) -> Unit,
    clearBackStack: () -> Unit,
) {
    composable(
        route = Screen.SelectInterest.route,
        enterTransition = { _, _ ->
            slideInHorizontally(
                initialOffsetX = { fullWidth -> fullWidth },
                animationSpec = tween(durationMillis = 300)
            )
        },
    ) {
        SelectInterestScreen(onNavigate, clearBackStack)
    }
}
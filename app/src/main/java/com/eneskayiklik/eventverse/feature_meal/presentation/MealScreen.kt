package com.eneskayiklik.eventverse.feature_meal.presentation

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.ViewModelStoreOwner
import androidx.navigation.NavGraphBuilder
import com.eneskayiklik.eventverse.R
import com.eneskayiklik.eventverse.core.util.Screen
import com.eneskayiklik.eventverse.core.util.anim.ScreensAnim.enterTransition
import com.eneskayiklik.eventverse.core.util.anim.ScreensAnim.exitTransition
import com.eneskayiklik.eventverse.core.util.anim.ScreensAnim.popEnterTransition
import com.eneskayiklik.eventverse.core.util.anim.ScreensAnim.popExitTransition
import com.eneskayiklik.eventverse.core.util.extension.shareMeal
import com.eneskayiklik.eventverse.core.util.modifier.pagerTabIndicatorOffset
import com.eneskayiklik.eventverse.feature_meal.presentation.component.CustomTab
import com.eneskayiklik.eventverse.feature_meal.presentation.component.ErrorMealView
import com.eneskayiklik.eventverse.feature_meal.presentation.component.MealToolbar
import com.eneskayiklik.eventverse.feature_meal.presentation.component.meal_list.DateItem
import com.eneskayiklik.eventverse.feature_meal.presentation.component.meal_list.MealSectionTitle
import com.eneskayiklik.eventverse.feature_meal.presentation.component.meal_list.SingleDishItem
import com.google.accompanist.insets.statusBarsPadding
import com.google.accompanist.navigation.animation.composable
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.rememberPagerState
import kotlinx.coroutines.launch

@Composable
private fun MealScreen(
    onNavigate: (String) -> Unit,
    clearBackStack: () -> Unit,
    viewModel: MealViewModel = hiltViewModel()
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colors.background)
    ) {
        MealToolbar(
            modifier = Modifier
                .fillMaxWidth()
                .background(MaterialTheme.colors.background)
                .statusBarsPadding()
                .height(56.dp)
        )

        Box(
            modifier = Modifier
                .fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            ItemsContent(viewModel, onNavigate)
        }
    }
}

@OptIn(
    ExperimentalPagerApi::class
)
@Composable
private fun BoxScope.ItemsContent(viewModel: MealViewModel, onNavigate: (String) -> Unit) {
    val state = viewModel.state.collectAsState().value
    val meals = state.mealList
    val scope = rememberCoroutineScope()
    val context = LocalContext.current

    when {
        state.isLoading -> {
            CircularProgressIndicator(
                modifier = Modifier
                    .size(32.dp)
                    .align(Alignment.Center),
                color = MaterialTheme.colors.primary,
                strokeWidth = 2.dp
            )
        }
        state.hasError -> {
            ErrorMealView(
                modifier = Modifier
                    .align(Alignment.Center)
                    .padding(32.dp),
                title = state.errorTitle,
                subtitle = state.errorSubtitle
            )
        }
        else -> {
            val pagerState = rememberPagerState(state.initialPage)
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(top = 5.dp)
            ) {
                ScrollableTabRow(selectedTabIndex = pagerState.currentPage,
                    backgroundColor = MaterialTheme.colors.background,
                    indicator = { tabPositions ->
                        TabRowDefaults.Indicator(
                            Modifier
                                .pagerTabIndicatorOffset(pagerState, tabPositions)
                                .zIndex(1F)
                                .padding(horizontal = 25.dp)
                                .clip(
                                    RoundedCornerShape(20.dp)
                                ),
                            height = 70.dp,
                            color = MaterialTheme.colors.primary
                        )
                    }, divider = { }) {
                    state.availableDates.forEachIndexed { index, title ->
                        CustomTab(
                            selected = pagerState.currentPage == index,
                            onClick = {
                                scope.launch {
                                    pagerState.scrollToPage(index)
                                }
                            },
                            title = title.day,
                            subtitle = title.date,
                            modifier = Modifier.zIndex(2F)
                        )
                    }
                }
                HorizontalPager(
                    count = meals.count(),
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(top = 5.dp),
                    state = pagerState
                ) { page ->
                    val currentItem = meals[page]
                    LazyColumn(
                        modifier = Modifier
                            .fillMaxSize(),
                        contentPadding = PaddingValues(
                            bottom = 56.dp,
                            top = 30.dp,
                            start = 24.dp,
                            end = 24.dp
                        ), verticalArrangement = Arrangement.spacedBy(40.dp)
                    ) {
                        item {
                            DateItem(text = currentItem.getFormattedDate()) {
                                context.shareMeal(currentItem)
                            }
                        }
                        item {
                            MealSectionTitle(text = stringResource(id = R.string.soup))
                            Spacer(modifier = Modifier.height(10.dp))
                            SingleDishItem(dish = currentItem.soup)
                        }

                        item {
                            MealSectionTitle(text = stringResource(id = R.string.main_dish))
                            Spacer(modifier = Modifier.height(10.dp))
                            currentItem.mainDish.forEachIndexed { index, dish ->
                                SingleDishItem(dish = dish)
                                if (index != currentItem.mainDish.lastIndex)
                                    Spacer(modifier = Modifier.height(20.dp))
                            }
                        }

                        item {
                            MealSectionTitle(text = stringResource(id = R.string.side_dish))
                            Spacer(modifier = Modifier.height(10.dp))
                            SingleDishItem(dish = currentItem.sideDish)
                        }

                        item {
                            MealSectionTitle(text = stringResource(id = R.string.extra))
                            Spacer(modifier = Modifier.height(10.dp))
                            SingleDishItem(dish = currentItem.side)
                            Spacer(modifier = Modifier.height(10.dp))
                        }
                    }
                }
            }
        }
    }
}

@ExperimentalAnimationApi
fun NavGraphBuilder.mealComposable(
    onNavigate: (String) -> Unit,
    clearBackStack: () -> Unit,
    owner: ViewModelStoreOwner
) {
    composable(
        route = Screen.Meal.route,
        exitTransition = { exitTransition() },
        popExitTransition = { popExitTransition() },
        popEnterTransition = { popEnterTransition() },
        enterTransition = { enterTransition() },
    ) {
        MealScreen(onNavigate, clearBackStack, hiltViewModel(owner))
    }
}
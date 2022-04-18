package com.eneskayiklik.eventverse.core.component

import androidx.compose.animation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.eneskayiklik.eventverse.R
import com.eneskayiklik.eventverse.util.BottomNavItem
import com.eneskayiklik.eventverse.util.Screen

@ExperimentalAnimationApi
@Composable
fun BaseScaffold(
    modifier: Modifier = Modifier,
    navController: NavController,
    showBottomBar: Boolean = true,
    state: ScaffoldState,
    bottomNavItems: List<BottomNavItem> = listOf(
        BottomNavItem(
            route = Screen.Home.route,
            icon = R.drawable.ic_education,
            contentDescription = stringResource(id = R.string.explore)
        ),
        BottomNavItem(
            route = Screen.Polls.route,
            icon = R.drawable.ic_poll,
            contentDescription = stringResource(id = R.string.polls)
        ),
        BottomNavItem(
            route = Screen.Meal.route,
            icon = R.drawable.ic_meal,
            contentDescription = stringResource(id = R.string.meal)
        ),
        BottomNavItem(
            route = Screen.Announcement.route,
            icon = R.drawable.ic_megaphone,
            contentDescription = stringResource(id = R.string.announcement)
        ),
    ),
    onFabClick: () -> Unit = {},
    content: @Composable () -> Unit
) {
    var isClicked by remember { mutableStateOf(false) }
    Scaffold(
        bottomBar = {
            AnimatedVisibility(
                visible = showBottomBar,
                enter = slideInVertically(
                    initialOffsetY = { it }
                ),
                exit = slideOutVertically(
                    targetOffsetY = { it }
                )
            ) {
                BottomAppBar(
                    modifier = Modifier
                        .shadow(elevation = 0.dp, clip = false, shape = CircleShape),
                    backgroundColor = MaterialTheme.colors.secondary,
                ) {
                    Row(
                        horizontalArrangement = Arrangement.SpaceEvenly,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        bottomNavItems.forEachIndexed { _, bottomNavItem ->
                            BaseBottomNavItem(
                                icon = bottomNavItem.icon,
                                contentDescription = bottomNavItem.contentDescription,
                                selected = navController.currentBackStackEntry?.destination?.route == bottomNavItem.route,
                            ) {
                                val destination = navController.currentDestination?.route
                                if (destination != bottomNavItem.route) {
                                    navController.navigate(bottomNavItem.route) {
                                        popUpTo(Screen.Home.route)
                                        launchSingleTop = true
                                    }
                                }
                            }
                        }
                    }
                }
            }
        },
        scaffoldState = state,
        floatingActionButton = {
            AnimatedVisibility(
                visible = showBottomBar,
                enter = scaleIn() + fadeIn(),
                exit = scaleOut() + fadeOut()
            ) {
                MainFloatingActionGroup(isClicked, onNavigate = {
                    navController.navigate(it)
                    isClicked = isClicked.not()
                }, onFabClick = {
                    isClicked = isClicked.not()
                })
            }
        },
        isFloatingActionButtonDocked = false,
        floatingActionButtonPosition = FabPosition.End,
        modifier = modifier,
    ) { padding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
        ) {
            content()
        }
    }
}
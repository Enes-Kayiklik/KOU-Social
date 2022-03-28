package com.eneskayiklik.eventverse.core.component

import androidx.compose.animation.*
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.outlined.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.eneskayiklik.eventverse.R
import com.eneskayiklik.eventverse.core.ui.theme.White
import com.eneskayiklik.eventverse.core.util.BottomNavItem
import com.eneskayiklik.eventverse.core.util.Screen

@ExperimentalAnimationApi
@Composable
fun BaseScaffold(
    modifier: Modifier = Modifier,
    navController: NavController,
    showBottomBar: Boolean = true,
    state: ScaffoldState,
    bottomNavItems: List<BottomNavItem> = listOf(
        BottomNavItem(
            route = Screen.Explore.route,
            icon = Icons.Outlined.Explore,
            contentDescription = stringResource(id = R.string.explore)
        ),
        BottomNavItem(
            route = Screen.Calendar.route,
            icon = Icons.Outlined.CalendarToday,
            contentDescription = stringResource(id = R.string.calendar)
        ),
        BottomNavItem(
            route = Screen.MessageList.route,
            icon = Icons.Outlined.Message,
            contentDescription = stringResource(id = R.string.message)
        ),
        BottomNavItem(
            route = Screen.Map.route,
            icon = Icons.Outlined.LocationOn,
            contentDescription = stringResource(id = R.string.map)
        ),
        BottomNavItem(
            route = Screen.Profile.route(true),
            icon = Icons.Outlined.Person,
            contentDescription = stringResource(id = R.string.profile)
        ),
    ),
    onFabClick: () -> Unit = {},
    content: @Composable () -> Unit
) {
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
                    modifier = Modifier.shadow(elevation = 0.dp, clip = false, shape = CircleShape),
                    backgroundColor = MaterialTheme.colors.surface,
                ) {
                    Row(
                        horizontalArrangement = Arrangement.SpaceAround,
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
                                        popUpTo(Screen.Explore.route)
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
                FloatingActionButton(
                    backgroundColor = MaterialTheme.colors.primary,
                    onClick = onFabClick
                ) {
                    Icon(
                        imageVector = Icons.Default.Add,
                        tint = White,
                        contentDescription = stringResource(id = R.string.create_event)
                    )
                }
            }
        },
        isFloatingActionButtonDocked = false,
        floatingActionButtonPosition = FabPosition.End,
        modifier = modifier,
    ) {
        content()
    }
}
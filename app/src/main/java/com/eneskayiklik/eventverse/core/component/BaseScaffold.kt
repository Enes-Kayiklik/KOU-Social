package com.eneskayiklik.eventverse.core.component

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.outlined.CalendarToday
import androidx.compose.material.icons.outlined.Explore
import androidx.compose.material.icons.outlined.Map
import androidx.compose.material.icons.outlined.Person
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.eneskayiklik.eventverse.R
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
            route = Screen.Timeline.route,
            icon = Icons.Outlined.Explore,
            contentDescription = "Home"
        ),
        BottomNavItem(
            route = Screen.Calendar.route,
            icon = Icons.Outlined.CalendarToday,
            contentDescription = "Message"
        ),
        BottomNavItem(),
        BottomNavItem(
            route = Screen.Map.route,
            icon = Icons.Outlined.Map,
            contentDescription = "Activity"
        ),
        BottomNavItem(
            route = Screen.Profile.route,
            icon = Icons.Outlined.Person,
            contentDescription = "Profile"
        ),
    ),
    onFabClick: () -> Unit = {},
    content: @Composable () -> Unit
) {
    Scaffold(
        bottomBar = {
            AnimatedVisibility(visible = showBottomBar) {
                BottomAppBar(
                    modifier = Modifier
                        .fillMaxWidth(),
                    elevation = 5.dp,
                    backgroundColor = MaterialTheme.colors.background,
                ) {
                    BottomNavigation {
                        bottomNavItems.forEachIndexed { _, bottomNavItem ->
                            BaseBottomNavItem(
                                modifier = Modifier.background(MaterialTheme.colors.background),
                                icon = bottomNavItem.icon,
                                contentDescription = bottomNavItem.contentDescription,
                                selected = navController.currentDestination?.route?.startsWith(
                                    bottomNavItem.route
                                ) == true,
                                enabled = bottomNavItem.icon != null
                            ) {
                                if (navController.currentDestination?.route != bottomNavItem.route) {
                                    navController.navigate(bottomNavItem.route)
                                }
                            }
                        }
                    }
                }
            }
        },
        scaffoldState = state,
        floatingActionButton = {
            AnimatedVisibility(visible = showBottomBar) {
                FloatingActionButton(
                    backgroundColor = MaterialTheme.colors.primary,
                    onClick = onFabClick
                ) {
                    Icon(
                        imageVector = Icons.Default.Add,
                        contentDescription = stringResource(id = R.string.create_event)
                    )
                }
            }
        },
        isFloatingActionButtonDocked = true,
        floatingActionButtonPosition = FabPosition.Center,
        modifier = modifier,
        content = { content() }
    )
}
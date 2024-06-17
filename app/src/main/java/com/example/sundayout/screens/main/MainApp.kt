package com.example.sundayout.screens.main

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.Icon
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.sundayout.R
import com.example.sundayout.screens.main.home.HomeScreen
import com.example.sundayout.screens.main.home.HomeViewModel
import com.example.sundayout.screens.main.profile.ProfileScreen

sealed class BottomNavItem(val route: String, val resourceId: Int) {
    object Search: BottomNavItem("search", R.string.navbarSeach)
    object Profile: BottomNavItem("profile", R.string.navBarProfile)
}

@Composable
fun BottomTabBar(
    tabBarItems: List<BottomNavItem>,
    navController: NavHostController
) {
    BottomNavigation(
        backgroundColor = Color.White,
        modifier = Modifier
            .fillMaxWidth()
    ) {
        val backStackEntry by navController.currentBackStackEntryAsState()
        val currentRoute = backStackEntry?.destination

        tabBarItems.forEach { screen ->
            BottomNavigationItem(
                icon = {
                    Icon(Icons.Filled.Favorite, contentDescription = null)
                },
                label = {
                    Text(stringResource(screen.resourceId))
                },
                selected = currentRoute?.hierarchy?.any { it.route == screen.route } == true,
                onClick = {
                    navController.navigate(screen.route) {
                        popUpTo(navController.graph.findStartDestination().id) {
                            saveState = true
                        }
                        launchSingleTop = true
                        restoreState = true
                    }
                }
            )
        }
    }
}


@Composable
fun MainApp(
    navController: NavHostController = rememberNavController()
) {
    val items = listOf(
        BottomNavItem.Search,
        BottomNavItem.Profile
    )
    Scaffold(
        bottomBar = {
            BottomTabBar(items, navController)
        },
        modifier = Modifier
            .fillMaxSize()
    ) { innerPadding ->
        NavHost(navController = navController, startDestination = BottomNavItem.Search.route, modifier = Modifier.padding(innerPadding)) {
            composable(route = BottomNavItem.Search.route) {
                val homeViewModel: HomeViewModel = viewModel(factory = HomeViewModel.Factory)
                HomeScreen(homeViewModel = homeViewModel, businessesUiState = homeViewModel.businessesUiState)
            }
            composable(route = BottomNavItem.Profile.route) {
                ProfileScreen()
            }
        }
    }
}
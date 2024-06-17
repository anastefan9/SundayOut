package com.example.sundayout.screens.auth

import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.sundayout.screens.auth.login.LogInScreen
import com.example.sundayout.screens.auth.register.SignInScreen
import com.example.sundayout.screens.main.home.HomeScreen
import com.example.sundayout.screens.main.home.HomeViewModel


enum class NavigationSundayOut(val title: String) {
    LogIn("LogIn"),
    SignIn("SignIn"),
    Verification("Verification"),
    Home("Home")
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NavTopBar(
    currentScreen: NavigationSundayOut,
    canNavigateBack: Boolean,
    navigateUp: () -> Unit = {},
    modifier: Modifier = Modifier
) {
    TopAppBar(
        title = {},
        navigationIcon = {
            if (canNavigateBack) {
                IconButton(onClick = { navigateUp() }) {
                    Icon(
                        imageVector = Icons.Filled.ArrowBack,
                        contentDescription = null
                    )
                }
            }
        }
    )
}

@Composable
fun AuthApp (
    navController: NavHostController = rememberNavController()
) {
    val backStackEntry by navController.currentBackStackEntryAsState()
    val currentScreen = NavigationSundayOut.valueOf(
        backStackEntry?.destination?.route ?: NavigationSundayOut.LogIn.name
    )

    Scaffold(
        topBar = {
            NavTopBar(
                currentScreen = currentScreen,
                canNavigateBack =  navController.previousBackStackEntry != null,
                navigateUp = {
                    navController.navigateUp()
                }
            )
        }
    ) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = NavigationSundayOut.LogIn.name,
            modifier = Modifier.padding(innerPadding)
        ) {

            composable(route = NavigationSundayOut.LogIn.name) {
                LogInScreen(toSignIn = {
                    navController.navigate(NavigationSundayOut.SignIn.name)
                })
            }
            composable(route = NavigationSundayOut.SignIn.name) {
                SignInScreen(navController = navController)
            }
            composable(route = NavigationSundayOut.Home.name) {
                val homeViewModel: HomeViewModel = viewModel(factory = HomeViewModel.Factory)
                HomeScreen(homeViewModel = homeViewModel, businessesUiState = homeViewModel.businessesUiState)
            }
        }
    }
}
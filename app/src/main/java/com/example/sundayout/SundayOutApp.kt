package com.example.sundayout

import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier

import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.sundayout.screens.LogInScreen
import com.example.sundayout.screens.SignInScreen
import com.example.sundayout.screens.VerificationScreen

enum class NavigationSundayOut(val title: String) {
    LogIn("LogIn"),
    SignIn("SignIn"),
    Verification("Verification")
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NavTopBar(
    currentScreen:NavigationSundayOut,
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
fun SundayOutApp(
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
                SignInScreen()
            }
            composable(route = NavigationSundayOut.Verification.name) {
                VerificationScreen()
            }
        }
    }
}
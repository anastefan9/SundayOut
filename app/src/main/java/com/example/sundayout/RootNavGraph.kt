package com.example.sundayout

import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NamedNavArgument
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.example.sundayout.screens.auth.login.LogInScreen
import com.example.sundayout.screens.auth.register.SignInScreen
import com.example.sundayout.screens.auth.register.SignInViewModel
import com.example.sundayout.screens.main.home.HomeScreen
import com.example.sundayout.screens.main.home.HomeViewModel

sealed class Destination(
    val route: String,
    val title: Int? = null,
    val selectedIcon: ImageVector? = null,
    val unselectedIcon: ImageVector? = null,
    val navArguments: List<NamedNavArgument> = emptyList()
)
private object Routes {
    // FIRST GRAPH ROUTES
    const val auth = "auth"
    const val login = "login"
    const val signup = "signup"

    // MAIN GRAPH ROUTES
    const val main = "main"
    const val home = "home"
}

sealed class AppScreen(val route: String) {
    object Auth: AppScreen(Routes.auth) {
        object Login: AppScreen(Routes.login)
        object Signup: AppScreen(Routes.signup)
    }
    object Main: Destination(route = Routes.main) {
        object Home: Destination(
            route = Routes.home,
            title = R.string.home_screen
        )
    }
}

fun NavGraphBuilder.authNavGraph(
    navController: NavHostController
) {
    navigation(
        route = AppScreen.Auth.route,
        startDestination = AppScreen.Auth.Login.route
    ) {
        composable(route = AppScreen.Auth.Login.route) {
            LogInScreen(toSignIn = { navController.navigate(AppScreen.Auth.Signup.route) })
        }
        composable(route = AppScreen.Auth.Signup.route) {
            val signInViewModel: SignInViewModel = hiltViewModel()
            SignInScreen(signInViewModel = signInViewModel, navToMainScreen = {
                navController.navigate(AppScreen.Main.route) {
                    popUpTo(AppScreen.Auth.route) { inclusive = true }
                }
            })
        }
    }
}

fun NavGraphBuilder.mainNavGraph(
    navController: NavHostController
) {
    navigation(
        route = AppScreen.Main.route,
        startDestination = AppScreen.Main.Home.route
    ) {
        composable(route = AppScreen.Main.Home.route) {
            val homeViewModel: HomeViewModel = hiltViewModel()
            HomeScreen(
                homeViewModel = homeViewModel,
                businessesUiState = homeViewModel.businessesUiState
            )
        }
    }
}

@Composable
fun RootNavGraph(navHostController: NavHostController) {
    NavHost(navController = navHostController, startDestination = AppScreen.Auth.route) {
        authNavGraph(navHostController)
        mainNavGraph(navHostController)
    }
}
package com.example.sundayout.navigation

import android.os.Bundle
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update

sealed interface AppNav {
    val extras: Bundle?

    data class Auth(override val extras: Bundle? = null) : AppNav
    data class Main(override val extras: Bundle? = null) : AppNav
}
class AppNavController {
    val appNavState = MutableStateFlow<AppNav>(AppNav.Auth())

    fun open(appNav: AppNav) {
        appNavState.update {
            appNav
        }
    }
}
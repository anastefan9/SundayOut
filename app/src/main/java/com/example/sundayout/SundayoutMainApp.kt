package com.example.sundayout

import androidx.compose.animation.AnimatedContent
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import com.example.sundayout.navigation.AppNav
import com.example.sundayout.screens.auth.AuthApp
import com.example.sundayout.screens.main.MainApp
import androidx.lifecycle.viewmodel.compose.viewModel

@Composable
fun SundayoutMainApp(
    viewModel: SundayoutMainViewModel = viewModel()
) {
    val appNavState by viewModel.appNavState.collectAsState()

    Box(
        modifier = Modifier
            .navigationBarsPadding()
            .imePadding()
    ) {
        AnimatedContent(
            targetState = appNavState,
            label = "Main/Auth Switcher",
        ) { targetState ->
            when (targetState) {
                is AppNav.Auth -> AuthApp()
                is AppNav.Main -> MainApp()
            }
        }
    }
}
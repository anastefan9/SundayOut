package com.example.sundayout.screens.auth

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.sundayout.SundayoutMainViewModel
import com.example.sundayout.navigation.AppNav
import com.example.sundayout.navigation.AppNavController

class AuthViewModel (private val appNavController: AppNavController): ViewModel() {
    fun navigateToMain() {
        appNavController.open(AppNav.Main())
    }

    companion object {
        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val appNavController = AppNavController()
               AuthViewModel(appNavController = appNavController)
            }
        }
    }
}
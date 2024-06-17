package com.example.sundayout

import android.text.Spannable.Factory
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.sundayout.navigation.AppNavController
import kotlinx.coroutines.flow.asStateFlow

class SundayoutMainViewModel(private val appNavController: AppNavController): ViewModel() {
    val appNavState = appNavController.appNavState.asStateFlow()

    companion object {
        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val appNavController = AppNavController()
                SundayoutMainViewModel(appNavController = appNavController)
            }
        }
    }
}
package com.example.sundayout.screens.auth.login

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel

class LogInViewModel: ViewModel() {
    var userEmail by mutableStateOf("")
        private set

    var userPassword by mutableStateOf("")
        private set

    fun updateEmailField(email: String) {
        userEmail = email
    }

    fun updatePasswordField(password: String) {
        userPassword = password
    }
}
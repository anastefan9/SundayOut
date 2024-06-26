package com.example.sundayout.screens.auth.register

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.sundayout.data.SignUpRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

sealed class SignUpEvent {
    object NavigateHome: SignUpEvent()
}

@HiltViewModel
class SignInViewModel @Inject constructor(
    private val signUpRepository: SignUpRepository,
): ViewModel() {
    private val _uiState = MutableStateFlow(PasswordValidationState())
    val uiState: StateFlow<PasswordValidationState> = _uiState.asStateFlow()

    private val _signUpEvent = MutableLiveData<SignUpEvent>()
    val signUpEvent: LiveData<SignUpEvent> = _signUpEvent

    var userFirstName by mutableStateOf("")
        private set

    var userSecondName by mutableStateOf("")
        private set

    var userEmail by mutableStateOf("")
        private set

    var userPassword by mutableStateOf("")
        private set

    fun updateFirstName(firstName: String) {
        userFirstName = firstName
    }

    fun updateSecondName(secondName: String) {
        userSecondName = secondName
    }

    fun updateEmail(email: String) {
        userEmail = email
    }

    fun updatePassword(password: String) {
        userPassword = password
    }

    fun isPasswordValid(): Boolean {
        return userPassword.matches(Regex("^(?=.*?[A-Z])(?=.*?[a-z])(?=.*?[0-9])(?=.*?[#?!@\$%^&*-]).{8,}\$"))
    }

    fun validateSpecialCharacter(): Boolean {
        return userPassword.matches(Regex(".*[@#$%^&+=].*"))
    }

    fun validateUpperCharacter(): Boolean {
        return userPassword.matches(Regex(".*[A-Z].*"))
    }

    fun validateLowerCharacter(): Boolean {
        return userPassword.matches(Regex(".*[a-z].*"))
    }

    fun validateDigit(): Boolean {
        return userPassword.matches(Regex(".*[0-9].*"))
    }

    fun validateMinLength(): Boolean {
        return userPassword.matches(Regex(".{8,}"))
    }

    fun isPasswordFieldFocused() {
        _uiState.update { currentState ->
            currentState.copy(
                isPasswordFieldFocused = !currentState.isPasswordFieldFocused
            )
        }
    }

    fun setPasswordState() {
        _uiState.update { currentState ->
            currentState.copy(
                isVerified = true
            )
        }
    }

    fun checkPassword() {
        _uiState.update { currentState ->
            currentState.copy(
                hasDigit = validateDigit(),
                hasLowerCharacter = validateLowerCharacter(),
                hasUpperCharacter = validateUpperCharacter(),
                hasMinLength = validateMinLength(),
                hasSpecialCharacter = validateSpecialCharacter(),
                isPasswordValid = isPasswordValid()
            )
        }
    }

    fun signup() {
        viewModelScope.launch {
            try {
                val result = signUpRepository.signupUser(
                    firstName = userFirstName,
                    lastName = userSecondName,
                    email = userEmail,
                    password = userPassword)
                if (result.isSuccessful) {
                    println("success signup")
                    _signUpEvent.value = SignUpEvent.NavigateHome
                } else {
                    println("fail to signup")
                }
            } catch (e: Exception) {
                println("Signup failed: ${e.message}")
            }
        }
    }
}
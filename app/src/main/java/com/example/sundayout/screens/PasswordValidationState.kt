package com.example.sundayout.screens

data class PasswordValidationState(
    val isPasswordValid: Boolean = false,
    val isVerified: Boolean = false,
    val hasMinLength: Boolean = false,
    val hasUpperCharacter: Boolean = false,
    val hasLowerCharacter: Boolean = false,
    val hasDigit: Boolean = false,
    val hasSpecialCharacter: Boolean = false,
    val isPasswordFieldFocused: Boolean = true
)
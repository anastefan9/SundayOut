package com.example.sundayout.screens.auth.register

import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.example.sundayout.R
import com.example.sundayout.screens.auth.NavigationSundayOut
import com.example.sundayout.ui.theme.interFontFamily
import com.example.sundayout.utils.rememberImeState

@Composable
fun SignInScreen(
    navController: NavHostController,
    signInViewModel: SignInViewModel = viewModel(factory = SignInViewModel.Factory),
    modifier: Modifier = Modifier
        .fillMaxSize()
) {
    val passwordValidationState = signInViewModel.uiState.collectAsState()
    val focusManager = LocalFocusManager.current
    val imeState = rememberImeState()
    val scrollState = rememberScrollState()
    val focusedTextField = rememberFocusedTextFieldState()

    val navigateToMainScreen by signInViewModel.navigateToMainScreen.observeAsState(false)

    // Trigger navigation if navigateToMainScreen is true
    if (navigateToMainScreen) {
        LaunchedEffect(Unit) {
            navController.navigate(NavigationSundayOut.Home.name) {
                popUpTo(NavigationSundayOut.SignIn.name) { inclusive = true }
            }
            signInViewModel.resetNavigationState() // Reset navigation state
        }
    }

    LaunchedEffect(key1 = imeState.value, key2 = focusedTextField.value) {
        val targetScroll = focusedTextField.value * 320
        scrollState.animateScrollTo(targetScroll, tween(300))
    }

    Column(
        modifier = Modifier
            .verticalScroll(scrollState)
            .fillMaxSize()
            .padding(horizontal = 30.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            painter = painterResource(R.drawable.sundayout),
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .padding(bottom = 64.dp)
        )

        CustomTextField(
            value = signInViewModel.userFirstName,
            index = 0,
            label = stringResource(R.string.textField_first_name),
            updateTextField = {
                signInViewModel.updateFirstName(it)
            },
            focusedTextField = focusedTextField,
            keyBoardType = KeyboardType.Text,
            keyboardAction = KeyboardActions(
                onDone = { focusManager.clearFocus() }
            )
        )

        CustomTextField(
            value = signInViewModel.userSecondName,
            index = 1,
            label = stringResource(R.string.textField_second_name),
            updateTextField = {
                signInViewModel.updateSecondName(it)
            },
            focusedTextField = focusedTextField,
            keyBoardType = KeyboardType.Text,
            keyboardAction = KeyboardActions(
                onDone = { focusManager.clearFocus() }
            )
        )

        CustomTextField(
            value = signInViewModel.userEmail,
            index = 2,
            updateTextField = {
                signInViewModel.updateEmail(it)
            },
            label = stringResource(R.string.textField_email),
            keyBoardType = KeyboardType.Email,
            focusedTextField = focusedTextField,
            keyboardAction = KeyboardActions(
                onDone = { focusManager.clearFocus() }
            )
        )

        CustomTextField(
            value = signInViewModel.userPassword,
            index = 3,
            checkPassword = {
                signInViewModel.checkPassword()
            },
            updateTextField = {
                signInViewModel.updatePassword(it)
            },
            updateTextFieldFocus = {
                signInViewModel.isPasswordFieldFocused()
            },
            hasVisualTransformation = true,
            label = stringResource(R.string.textField_password),
            keyBoardType = KeyboardType.Password,
            focusedTextField = focusedTextField,
            keyboardAction = KeyboardActions(
                onDone = { focusManager.clearFocus() }
            )
        )

        if (passwordValidationState.value.isPasswordFieldFocused) {
            ValidatePasswordBannerView(
                passwordState = passwordValidationState
            )
        }

        Button(
            onClick = {
                signInViewModel.setPasswordState()
                if (signInViewModel.isPasswordValid()) {
                    signInViewModel.signup()
                    //toMainScreen()
                }
            },
            shape = RoundedCornerShape(10.dp),
            colors = ButtonDefaults.buttonColors(colorResource(R.color.main_blue)),
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 20.dp)
                .height(48.dp)
        ) {
            Text(
                text = stringResource(R.string.continueButton),
                color = Color.White,
                fontWeight = FontWeight.Bold,
                fontSize = 16.sp
            )
        }
    }
}

@Composable
fun rememberFocusedTextFieldState(): MutableState<Int> {
    return remember { mutableStateOf(0) }
}

@Composable
fun CustomTextField(
    value: String,
    index: Int,
    label: String,
    hasVisualTransformation: Boolean = false,
    updateTextField: (text: String) -> Unit,
    checkPassword: ((password: String) -> Unit)? = {},
    focusedTextField: MutableState<Int>,
    updateTextFieldFocus: (() -> Unit)? = {},
    keyBoardType: KeyboardType,
    keyboardAction: KeyboardActions
) {
    var isFocused by remember { mutableStateOf(false) }

    OutlinedTextField(
        value = value,
        singleLine = true,
        shape = RoundedCornerShape(10.dp),
        visualTransformation = if (hasVisualTransformation) PasswordVisualTransformation() else VisualTransformation.None,
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 10.dp)
            .height(60.dp)
            .onFocusChanged {
                updateTextFieldFocus?.invoke()
                isFocused = it.isFocused
            }
            .onGloballyPositioned {
                if (isFocused) {
                    focusedTextField.value = index
                }
            },
        colors = OutlinedTextFieldDefaults.colors(
            unfocusedBorderColor = colorResource(R.color.textField_border_unfocused),
            focusedBorderColor = colorResource(R.color.main_blue),
            unfocusedLabelColor = colorResource(R.color.textField_border_unfocused),
            focusedLabelColor = colorResource(R.color.main_blue)
        ),
        onValueChange = {
            updateTextField(it)
            checkPassword?.invoke(it)
        },
        label = {
            Text(label)
        },
        keyboardOptions = KeyboardOptions(keyboardType = keyBoardType),
        keyboardActions = keyboardAction
    )
}


@Composable
fun PasswordRequirementRow(
    tintColor: Color,
    title: String
) {
    Row(
        horizontalArrangement = Arrangement.Absolute.Left,
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 20.dp)
            .padding(bottom = 4.dp)
    ) {
        Icon(
            painter = painterResource(R.drawable.ic_circle),
            contentDescription = null,
            tint = tintColor,
            modifier = Modifier
                .width(6.dp)
                .height(6.dp)
        )
        Text(
            text = title,
            fontSize = 14.sp,
            color = tintColor,
            modifier = Modifier
                .padding(start = 8.dp)
        )
    }
}

@Composable
fun getPasswordTextColor(isValid: Boolean, isVerified: Boolean): Color {
    if (isVerified && !isValid)
        return Color.Red
    else if (isValid)
        return colorResource(R.color.main_blue)
    else
        return colorResource(R.color.primary_gray)
}

@Composable
fun ValidatePasswordBannerView(
    passwordState: State<PasswordValidationState>,
    modifier: Modifier = Modifier.padding(top = 10.dp)
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(10.dp))
            .border(
                width = 1.dp,
                color = colorResource(R.color.container_border),
                shape = RoundedCornerShape(10.dp)
            )
            .background(color = colorResource(R.color.container_backgroud))
            .padding(bottom = 12.dp)
    ) {
        Text(
            text = if (passwordState.value.isVerified && !passwordState.value.isPasswordValid) stringResource(R.string.passwordBannerTitleInvalid)
                   else if (passwordState.value.isPasswordValid) stringResource(R.string.passwordBannerTitleValid)
                   else stringResource(R.string.passwordBannerTitleUnverified),
            fontSize = 14.sp,
            fontFamily = interFontFamily,
            fontWeight = FontWeight.Medium,
            color = getPasswordTextColor(
                isValid = passwordState.value.isPasswordValid,
                isVerified = passwordState.value.isVerified
            ),
            modifier = Modifier
                .padding(top = 12.dp)
                .padding(start = 20.dp)
                .padding(bottom = 6.dp)
        )
        PasswordRequirementRow(
            tintColor = getPasswordTextColor(
                isValid = passwordState.value.hasMinLength,
                isVerified = passwordState.value.isVerified),
            title = stringResource(R.string.passwordBannerMinLength)
        )
        PasswordRequirementRow(
            tintColor = getPasswordTextColor(
                isValid = passwordState.value.hasLowerCharacter,
                isVerified = passwordState.value.isVerified),
            title = stringResource(R.string.passwordBannerLowercaseChar)
        )
        PasswordRequirementRow(
            tintColor = getPasswordTextColor(
                isValid = passwordState.value.hasUpperCharacter,
                isVerified = passwordState.value.isVerified),
            title = stringResource(R.string.passwordBannerUppercaseChar)
        )

        PasswordRequirementRow(
            tintColor = getPasswordTextColor(
                isValid = passwordState.value.hasDigit,
                isVerified = passwordState.value.isVerified),
            title = stringResource(R.string.passwordBannerDigit)
        )

        PasswordRequirementRow(
            tintColor = getPasswordTextColor(
                isValid = passwordState.value.hasSpecialCharacter,
                isVerified = passwordState.value.isVerified),
            title = stringResource(R.string.passwordBannerSpecialChar)
        )
    }
}
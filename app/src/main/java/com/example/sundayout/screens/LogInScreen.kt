package com.example.sundayout.screens

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.sundayout.R
import com.example.sundayout.extensions.nativeGradient

@Composable
fun LogInScreen(
    logInViewModel: LogInViewModel = viewModel(),
    toSignIn: () -> Unit,
    modifier: Modifier = Modifier
        .fillMaxSize()
) {
    val focusManager = LocalFocusManager.current

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 30.dp)
    ) {
        Image(
            painter = painterResource(R.drawable.sundayout),
            contentDescription = null,
            contentScale = ContentScale.Crop
        )

        OutlinedTextField(
            value = logInViewModel.userEmail,
            singleLine = true,
            shape = RoundedCornerShape(10.dp),
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 64.dp)
                .padding(bottom = 10.dp)
                .height(60.dp),
            colors = OutlinedTextFieldDefaults.colors(
                unfocusedBorderColor = colorResource(R.color.textField_border_unfocused),
                focusedBorderColor = colorResource(R.color.main_blue),
                unfocusedLabelColor = colorResource(R.color.textField_border_unfocused),
                focusedLabelColor = colorResource(R.color.main_blue)
            ),
            onValueChange = {
                logInViewModel.updateEmailField(it)
            },
            label = {
                Text(
                    stringResource(R.string.textField_email)
                )
            },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
            keyboardActions = KeyboardActions(
                onDone = { focusManager.clearFocus() }
            )
        )

        OutlinedTextField(
            value = logInViewModel.userPassword,
            singleLine = true,
            shape = RoundedCornerShape(10.dp),
            visualTransformation = PasswordVisualTransformation(),
            modifier = Modifier
                .fillMaxWidth()
                .height(60.dp),
            colors = OutlinedTextFieldDefaults.colors(
                unfocusedBorderColor = colorResource(R.color.textField_border_unfocused),
                focusedBorderColor = colorResource(R.color.main_blue),
                unfocusedLabelColor = colorResource(R.color.textField_border_unfocused),
                focusedLabelColor = colorResource(R.color.main_blue)
            ),
            onValueChange = {
                logInViewModel.updatePasswordField(it)
            },
            label = {
                Text(stringResource(R.string.textField_password))
            },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
            keyboardActions = KeyboardActions(
                onDone = { focusManager.clearFocus() }
            )
        )

        Button(
            onClick = { /*TODO*/ },
            shape = RoundedCornerShape(10.dp),
            colors = ButtonDefaults.buttonColors(colorResource(R.color.main_blue)),
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 20.dp)
                .height(48.dp)
        ) {
            Text(
                text = stringResource(R.string.logIn),
                color = Color.White,
                fontWeight = FontWeight.Bold,
                fontSize = 16.sp
            )
        }

        Spacer(modifier = Modifier.weight(1.0f))

        OutlinedButton(
            onClick = { toSignIn() },
            shape = RoundedCornerShape(10.dp),
            border = BorderStroke(
                width = 1.dp,
                brush = Brush.nativeGradient()
            ),
            modifier = Modifier
                .fillMaxWidth()
                .height(48.dp)
        ) {
            Text(
                text = stringResource(R.string.createNewAccount),
                color = Color.Black,
                fontWeight = FontWeight.Bold,
                fontSize = 16.sp
            )
        }
    }
}


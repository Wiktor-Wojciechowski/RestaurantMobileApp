package com.example.restaurantapp.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.getValue
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.restaurantapp.R
import com.example.restaurantapp.model.RegisterParams

@Composable
fun RegisterScreen (
    onGoToLogin: () -> Unit = {},
    onRegister: (RegisterParams) -> Unit = {},
    registrationState: RegistrationState
){
    when (registrationState) {

        is RegistrationState.Success -> onGoToLogin()
    }
    var username by remember { mutableStateOf("")}
    var email by remember { mutableStateOf("")}
    var password by remember { mutableStateOf("")}
    var age by remember { mutableStateOf("")}

    var isUsernameError by rememberSaveable { mutableStateOf(false) }
    var isEmailError by rememberSaveable { mutableStateOf(false) }
    var isPasswordError by rememberSaveable { mutableStateOf(false) }
    var isAgeError by rememberSaveable { mutableStateOf(false) }

    fun validateUsername(){
        isUsernameError = username.isEmpty()
    }
    fun validatePassword(){
        isPasswordError = password.isEmpty()
    }
    fun validateEmail(){
        isEmailError = email.isEmpty()
    }
    fun validateAge(){
        isAgeError = age.isEmpty()
    }
    fun validateCredentials(){
        validateUsername()
        validateEmail()
        validatePassword()
        validateAge()
    }

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ){
        Text(
            text = stringResource(R.string.register),
            fontSize = 30.sp
        )
        Spacer(modifier = Modifier.size(8.dp))
        OutlinedTextField(
            value = username,
            onValueChange = {
                username = it
                validateUsername()
            },
            label = { Text(text = stringResource(R.string.username))},
            supportingText = {
                if (isUsernameError){
                    Text(text = stringResource(R.string.empty_username_error), color = MaterialTheme.colorScheme.error)
                }
            },
            keyboardActions = KeyboardActions{validateUsername()}
        )
        OutlinedTextField(
            value = email,
            onValueChange = {
                email = it
                validateEmail()
            },
            label = { Text(text = stringResource(R.string.email))},
            supportingText = {
                if (isEmailError){
                    Text(text = stringResource(R.string.enter_the_email_error), color = MaterialTheme.colorScheme.error)
                }
            },
            keyboardActions = KeyboardActions{validateEmail()}
        )
        OutlinedTextField(
            value = password,
            onValueChange = {
                password = it
                validatePassword()
            },
            label = { Text(text = stringResource(R.string.password))},
            supportingText = {
                if (isPasswordError){
                    Text(text = stringResource(R.string.empty_password_error), color = MaterialTheme.colorScheme.error)
                }
            },
            keyboardActions = KeyboardActions{validatePassword()},
            visualTransformation = PasswordVisualTransformation(),
        )
        OutlinedTextField(
            value = age,
            onValueChange = {
                age = it
                validateAge()
            },
            label = { Text(text = stringResource(R.string.age))},
            supportingText = {
                if (isAgeError){
                    Text(text = stringResource(R.string.enter_the_age_error), color = MaterialTheme.colorScheme.error)
                }
            },
            keyboardActions = KeyboardActions{validateAge()}
        )
        Spacer(modifier = Modifier.size(8.dp))

        if (registrationState == RegistrationState.Loading){
            Text(text = stringResource(id = R.string.loading_ellipsis))
        }

        if (registrationState == RegistrationState.Error){
            Text(
                text = stringResource(id = R.string.error),
                color = MaterialTheme.colorScheme.error
            )
        }

        Button(onClick = {
            validateCredentials()
            if (!isUsernameError && !isEmailError && !isPasswordError && !isAgeError) {
                var params = RegisterParams(username, email, password, age)
                onRegister(params)
            }

        }) {
            Text(text = stringResource(R.string.register))
        }
        Button(onClick = {onGoToLogin()}){
            Text(
                text = stringResource(R.string.goToLogin)
            )
        }
    }
}

@Preview
@Composable
fun RegisterPreview(){
    RegisterScreen(registrationState = RegistrationState.Init)
}
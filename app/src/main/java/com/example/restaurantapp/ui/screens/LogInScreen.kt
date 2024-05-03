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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.restaurantapp.R
import com.example.restaurantapp.model.LoginParams
import com.example.restaurantapp.ui.theme.RestaurantAppTheme

@Composable
fun LogInScreen(
    loginState: LoginState = LoginState.Init,
    onGoToRegister: () -> Unit = {},
    onLogin: (params: LoginParams) -> Unit = {},
    onGoToHome: () -> Unit = {},
    modifier: Modifier = Modifier

) {

    var isLoginError by rememberSaveable { mutableStateOf(false) }
    var isUsernameError by rememberSaveable { mutableStateOf(false) }
    var isPasswordError by rememberSaveable { mutableStateOf(false) }
    var loginErrorMessage by rememberSaveable { mutableStateOf("error") }

    when(loginState){
        is LoginState.Success -> onGoToHome()
        is LoginState.Error -> {
            isLoginError = true
            loginErrorMessage = loginState.errorMessage
        }
        !is LoginState.Error -> {
            isLoginError = false
            loginErrorMessage = ""
        }
    }
    var username by remember {mutableStateOf("")}
    var password by remember { mutableStateOf("")}

    fun validateUsername(){
        isUsernameError = username.isEmpty()
    }
    fun validatePassword(){
        isPasswordError = password.isEmpty()
    }
    fun validateCredentials(){
        validateUsername()
        validatePassword()
    }

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = stringResource(R.string.login),
            fontSize = 30.sp
        )
        Spacer(modifier = Modifier.size(8.dp))
        OutlinedTextField(
            value = username,
            onValueChange = {
                username = it
                validateUsername()
            },
            label = { Text(stringResource(R.string.username)) },
            supportingText = {
                if (isUsernameError){
                    Text(text = stringResource(R.string.empty_username_error), color = MaterialTheme.colorScheme.error)
                }
            },
            keyboardActions = KeyboardActions{validateUsername()}
        )
        OutlinedTextField(
            value = password,
            onValueChange = {
                password = it
                validatePassword()
            },
            label = { Text(stringResource(R.string.password)) },
            supportingText = {
                if(isPasswordError) {
                    Text(text = stringResource(R.string.empty_password_error), color = MaterialTheme.colorScheme.error)
                }
            },
            keyboardActions = KeyboardActions{validatePassword()},
            visualTransformation = PasswordVisualTransformation(),
        )
        if(isLoginError){
            Text(
                text = loginErrorMessage,
                color = MaterialTheme.colorScheme.error
            )
        }
        if(loginState == LoginState.Loading){
            Text(text = "Loading...")
        }
        Spacer(modifier = Modifier.size(8.dp))
        Button(onClick = {
            validateCredentials()
            if(!isUsernameError && !isPasswordError){
                var params = LoginParams(username, password)
                onLogin(params)
            }
        }) {
            Text(
                text = stringResource(R.string.login)
            )
        }
        Button(onClick = { onGoToRegister() }) {
            Text(
                text = stringResource(R.string.goToRegister)
            )
        }
    }
}

@Preview
@Composable
fun LogInScreenPreview(

){
    RestaurantAppTheme {
        LogInScreen()
    }
}
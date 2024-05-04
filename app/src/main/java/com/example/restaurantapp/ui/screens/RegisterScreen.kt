package com.example.restaurantapp.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.getValue
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
    onRegister: (RegisterParams) -> Unit = {}
){
    var username by remember { mutableStateOf("")}
    var email by remember { mutableStateOf("")}
    var password by remember { mutableStateOf("")}
    var age by remember { mutableStateOf("")}
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
            onValueChange = {username = it},
            label = { Text(text = stringResource(R.string.username))})
        OutlinedTextField(
            value = email,
            onValueChange = {email = it},
            label = { Text(text = stringResource(R.string.email))})
        OutlinedTextField(
            value = password,
            visualTransformation = PasswordVisualTransformation(),
            onValueChange = {password = it},
            label = { Text(text = stringResource(R.string.password))})
        OutlinedTextField(
            value = age,
            onValueChange = {age = it},
            label = { Text(text = stringResource(R.string.age))})
        Spacer(modifier = Modifier.size(8.dp))
        Button(onClick = {
            var params = RegisterParams(username, email, password, age)
            onRegister(params)
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
    RegisterScreen()
}
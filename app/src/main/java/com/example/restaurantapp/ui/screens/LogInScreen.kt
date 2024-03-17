package com.example.restaurantapp.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.restaurantapp.R
import com.example.restaurantapp.ui.theme.RestaurantAppTheme

fun login(username:String, password:String){

}

@Composable
fun LogInScreen(
    onGoToRegister: () -> Unit = {},
    modifier: Modifier = Modifier

) {
    var username by remember {mutableStateOf("")}
    var password by remember { mutableStateOf("")}
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
            onValueChange = { username = it },
            label = { Text(stringResource(R.string.username)) }
        )
        OutlinedTextField(
            value = password,
            onValueChange = { password = it },
            label = { Text(stringResource(R.string.password)) }
        )
        Spacer(modifier = Modifier.size(8.dp))
        Button(onClick = {}) {
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
        LogInScreen(onGoToRegister = {})
    }
}
package com.example.restaurantapp.ui.screens

import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.restaurantapp.ui.theme.RestaurantAppTheme

@Composable
fun LogInScreen(
    onGoToRegister: () -> Unit = {},
    modifier: Modifier = Modifier
) {
    Button(onClick = {onGoToRegister()}){
        Text(
            text = "Go to Register"
        )
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
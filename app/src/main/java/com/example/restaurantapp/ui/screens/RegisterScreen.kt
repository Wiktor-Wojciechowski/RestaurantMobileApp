package com.example.restaurantapp.ui.screens

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable

@Composable
fun RegisterScreen (
    onGoToLogin: () -> Unit = {}
){
    Column(){
        TextField(value = "email", onValueChange = )
        Button(onClick = {onGoToLogin()}){
            Text(
                text = "Go to Login"
            )
        }
    }
}
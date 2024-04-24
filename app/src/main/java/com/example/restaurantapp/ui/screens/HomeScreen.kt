package com.example.restaurantapp.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment

@Composable
fun HomeScreen(
    onGoToOrder : () -> Unit = {},
    onGoToUserOrders : () -> Unit = {}
){
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ){
        Text(text = "Welcome")
        Button(onClick = {onGoToOrder()}){
            Text("Order food")
        }
        Button(onClick = {onGoToUserOrders()}){
            Text("My Orders")
        }
        Button(onClick = {}){
            Text("Log out")
        }

    }
}
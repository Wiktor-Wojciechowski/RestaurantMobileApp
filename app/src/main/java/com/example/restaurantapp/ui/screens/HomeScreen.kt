package com.example.restaurantapp.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.res.stringResource
import com.example.restaurantapp.R

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
            Text(stringResource(R.string.order_food_button))
        }
        Button(onClick = {onGoToUserOrders()}){
            Text(stringResource(R.string.my_orders_button))
        }
        Button(onClick = {}){
            Text(stringResource(R.string.log_out_button))
        }

    }
}
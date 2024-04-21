package com.example.restaurantapp.ui.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable

@Composable
fun FinalizeOrderScreen(
    onGoToHome: () -> Unit = {},
    orderViewModel: OrderViewModel,
    orderState: OrderState
){
    Column(){
        Column(){
            orderViewModel.getCart()
        }
        Card(){
            orderViewModel.getTable().toString()
        }
        Button(
            onClick = { orderViewModel.sendOrder() },
            enabled = orderState != OrderState.Success
        ) {
            Text(text = "Finalize Order")
        }
        if(orderState == OrderState.Error){
            Text(text = "Something went wrong")
        }
        Button(onClick = { onGoToHome() }) {
            Text(text = "Return Home")
        }
    }

}
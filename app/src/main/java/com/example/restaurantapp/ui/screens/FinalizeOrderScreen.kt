package com.example.restaurantapp.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.res.stringResource
import com.example.restaurantapp.R

@Composable
fun FinalizeOrderScreen(
    onGoToHome: () -> Unit = {},
    orderViewModel: OrderViewModel,
    orderState: OrderState
){
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ){
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
            Text(text = stringResource(R.string.finalize_order_button))
        }
        if(orderState == OrderState.Error){
            Text(text = stringResource(R.string.something_went_wrong_error))
        }
        Button(onClick = { onGoToHome() }) {
            Text(text = stringResource(R.string.return_home_button))
        }
    }

}
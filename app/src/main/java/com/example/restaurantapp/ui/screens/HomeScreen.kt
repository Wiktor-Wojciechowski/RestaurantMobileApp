package com.example.restaurantapp.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.restaurantapp.R

@Composable
fun HomeScreen(
    onGoToOrder : () -> Unit = {},
    onGoToUserOrders : () -> Unit = {},
    onGoToMakeReservation : () -> Unit = {},
    onGoToMyReservations : () -> Unit = {},
){
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = Modifier
            .widthIn(max = 350.dp)
    ){
        Text(
            text = stringResource(R.string.welcome_to_the_restaurant_heading),
            style = MaterialTheme.typography.headlineLarge,
            modifier = Modifier
                .padding(10.dp, 0.dp, 10.dp, 100.dp),
            textAlign = TextAlign.Center
        )
        Button(onClick = {onGoToOrder()}){
            Text(stringResource(R.string.order_food_button))
        }
        Button(onClick = {onGoToUserOrders()}){
            Text(stringResource(R.string.my_orders_button))
        }
        Button(onClick = {onGoToMakeReservation()}){
            Text(stringResource(R.string.make_a_reservation_button))
        }
        Button(onClick = {onGoToMyReservations()}){
            Text(stringResource(R.string.my_reservations_button))
        }

    }
}
package com.example.restaurantapp.ui.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun UserReservationsScreen(
    userReservationsState: UserReservationsState
){
    Column(
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Your Reservations",
            style = MaterialTheme.typography.headlineLarge
        )
        when (userReservationsState){
            is UserReservationsState.Loading -> Text(text = "Loading")
            is UserReservationsState.Error -> Text(text = "Error")
            is UserReservationsState.receivedReservations -> Column {
                if (!userReservationsState.reservations.isEmpty()){
                    userReservationsState.reservations.forEach { reservation ->
                        Card(
                            modifier = Modifier
                                .padding(8.dp)
                        ) {
                            Text(text = "From: " + reservation.from.toString())
                            Text(text = "To: " + reservation.to.toString())
                            Text(text = "Reserved Table Number: " + reservation.tableModelId.toString())
                        }
                    }

                } else {
                    Text(text = "No reservations")
                }

            }

        }
    }
}
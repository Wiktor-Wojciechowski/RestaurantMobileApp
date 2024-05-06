package com.example.restaurantapp.ui.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.restaurantapp.R

@Composable
fun UserReservationsScreen(
    userReservationsState: UserReservationsState
){
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .verticalScroll(rememberScrollState())
    ) {
        Text(
            text = stringResource(R.string.your_reservations_heading),
            style = MaterialTheme.typography.headlineLarge
        )
        when (userReservationsState){
            is UserReservationsState.Loading -> Text(text = stringResource(R.string.loading))
            is UserReservationsState.Error -> Text(text = stringResource(R.string.error))
            is UserReservationsState.receivedReservations -> Column {
                if (!userReservationsState.reservations.isEmpty()){
                    userReservationsState.reservations.forEach { reservation ->
                        Card(
                            modifier = Modifier
                                .padding(8.dp)
                        ) {
                            Column(
                                modifier = Modifier
                                    .padding(8.dp)
                            ) {
                                Text(text = stringResource(R.string.reservation_from) + reservation.from.toString())
                                Text(text = stringResource(R.string.reservation_to) + reservation.to.toString())
                                Text(text = stringResource(R.string.reservation_table) + reservation.tableModelId.toString())
                            }

                        }
                    }

                } else {
                    Text(text = stringResource(R.string.no_reservations))
                }

            }

        }
    }
}
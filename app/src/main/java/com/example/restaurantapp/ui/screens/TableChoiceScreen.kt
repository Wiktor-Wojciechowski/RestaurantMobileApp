package com.example.restaurantapp.ui.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import com.example.restaurantapp.R

@Composable
fun TableChoiceScreen(
    tablesState: TablesState = TablesState.Loading,
    infrastructureState: InfrastructureState = InfrastructureState.Loading
) {
    when (infrastructureState) {
        is InfrastructureState.Loading -> Text(stringResource(R.string.loading))
        is InfrastructureState.Error -> Text(stringResource(R.string.error))
        is InfrastructureState.receivedInfrastructure ->

            Column {
                when (tablesState) {
                    is TablesState.Loading -> Text(stringResource(R.string.loading))
                    is TablesState.Error -> Text(stringResource(R.string.error))
                    is TablesState.receivedTables -> Column {
                        tablesState.tables.forEach { table ->
                            Card {
                                Text(table.numberOfSeats.toString())
                            }

                        }
                    }
                }
            }
    }

}
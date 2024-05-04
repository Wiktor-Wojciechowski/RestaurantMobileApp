package com.example.restaurantapp.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import com.example.restaurantapp.R
import com.example.restaurantapp.model.Table

@Composable
fun TableChoiceScreen(
    tablesState: TablesState = TablesState.Loading,
    infrastructureState: InfrastructureState = InfrastructureState.Loading,
    viewModel: TableChoiceViewModel,
    onTableChosen: () -> Unit,
    orderViewModel: OrderViewModel
) {
    when (infrastructureState) {
        is InfrastructureState.Loading -> Text(stringResource(R.string.loading))
        is InfrastructureState.Error -> Text(stringResource(R.string.error))
        is InfrastructureState.receivedInfrastructure ->

            Column(
                modifier = Modifier
                    .verticalScroll(rememberScrollState())
                    .horizontalScroll(rememberScrollState())
            ){
                when (tablesState) {
                    is TablesState.Loading -> Text(stringResource(R.string.loading))
                    is TablesState.Error -> Text(stringResource(R.string.error))
                    is TablesState.receivedTables ->
                        Column {
                            Text(
                                text ="Choose Your Table",
                                style = MaterialTheme.typography.headlineLarge,
                            )
                            var rows = infrastructureState.infrastructure.numberOfRows
                            var columns = infrastructureState.infrastructure.numberOfColumns
                            var tables = tablesState.tables
                            repeat(rows){rowIndex ->
                                Row {
                                    repeat(columns){ columnIndex ->
                                        for (table in tables) {
                                            if(rowIndex == table.gridRow && columnIndex == table.gridColumn){
                                                TableSpot(
                                                    table = table,
                                                    viewModel = viewModel,
                                                    onTableChosen = {onTableChosen()},
                                                    orderViewModel = orderViewModel
                                                )
                                            }
                                        }

                                    }
                                }

                            }
                        }
                }
            }
    }

}

@Composable
fun TableSpot(
    table: Table,
    viewModel: TableChoiceViewModel,
    onTableChosen: () -> Unit = {},
    orderViewModel: OrderViewModel
){
    Card {
        Box{
            if(table.isAvailable){
                Image(painter = painterResource(id = R.drawable.available_table),
                    stringResource(R.string.an_available_table_description))

            }else{
                Image(painter = painterResource(id = R.drawable.unavailable_table),
                    stringResource(R.string.an_unavailable_table_description))
            }
        }

        Text("Number of seats: ${table.numberOfSeats}")
        if (!table.isAvailable) Text(stringResource(R.string.table_unavailable))
        Button(
            onClick = {
                viewModel.chosenTableId = table.id
                orderViewModel.setTable(table)
                onTableChosen()
                },
            enabled = table.isAvailable
        ){
            Text(stringResource(R.string.choose_button))
        }
    }
}
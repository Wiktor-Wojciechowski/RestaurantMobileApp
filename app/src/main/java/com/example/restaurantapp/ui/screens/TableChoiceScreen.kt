package com.example.restaurantapp.ui.screens

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import com.example.restaurantapp.R

@Composable
fun TableChoiceScreen(
    tablesState: TablesState = TablesState.Loading,
    infrastructureState: InfrastructureState = InfrastructureState.Loading,
    viewModel: TableChoiceViewModel,
    onTableChosen: () -> Unit
) {
    when (infrastructureState) {
        is InfrastructureState.Loading -> Text(stringResource(R.string.loading))
        is InfrastructureState.Error -> Text(stringResource(R.string.error))
        is InfrastructureState.receivedInfrastructure ->

            Column(
                modifier = Modifier
                    .verticalScroll(rememberScrollState())
            ){
                when (tablesState) {
                    is TablesState.Loading -> Text(stringResource(R.string.loading))
                    is TablesState.Error -> Text(stringResource(R.string.error))
                    is TablesState.receivedTables ->
                        Column {
                            var rows = infrastructureState.infrastructure.numberOfRows
                            var columns = infrastructureState.infrastructure.numberOfColumns
                            var tables = tablesState.tables
                            repeat(rows){rowIndex ->
                                Row(){
                                    repeat(columns){ columnIndex ->
                                        for (table in tables) {
                                            if(rowIndex == table.gridRow && columnIndex == table.gridColumn){
                                                TableSpot(
                                                    isAvailable = table.isAvailable,
                                                    seats = table.numberOfSeats,
                                                    viewModel = viewModel,
                                                    tableId = table.id,
                                                    onTableChosen = {onTableChosen()}
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
    isAvailable:Boolean,
    tableId:Int,
    seats: Int,
    viewModel: TableChoiceViewModel,
    onTableChosen: () -> Unit = {},
){
    Card(){
        Box{
            if(isAvailable){
                Image(painter = painterResource(id = R.drawable.available_table), "available table")

            }else{
                Image(painter = painterResource(id = R.drawable.unavailable_table), "unavailable table")
            }
        }

        Text("Number of seats: $seats")
        if (!isAvailable) Text("Table unavailable")
        Button(
            onClick = {
                viewModel.chosenTableId = tableId
                Log.d("chosenTable:", tableId.toString())
                onTableChosen()
                },
            enabled = isAvailable
        ){
            Text("Choose")
        }
    }
}
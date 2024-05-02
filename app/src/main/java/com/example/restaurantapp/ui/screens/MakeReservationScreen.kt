package com.example.restaurantapp.ui.screens

import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.DatePicker
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TimePicker
import androidx.compose.material3.TimePickerState
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.material3.rememberTimePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.example.restaurantapp.data.AuthContext
import com.example.restaurantapp.model.Infrastructure
import com.example.restaurantapp.model.SentReservation
import com.example.restaurantapp.model.Table
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.TimeZone

@ExperimentalMaterial3Api
@Composable
fun MakeReservationScreen(
    onMakeReservation: (reservation: SentReservation) -> Unit = {},
    reservationState: Any,
    tablesState: TablesState = TablesState.Loading,
    infrastructureState: InfrastructureState = InfrastructureState.Loading,
) {
    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        val datePickerState = rememberDatePickerState(initialSelectedDateMillis = System.currentTimeMillis())
        val timerState = rememberTimePickerState()
        val openTimer = remember { mutableStateOf(false)}
        val openDatePicker = remember { mutableStateOf(false)}
        val openTableChoice = remember { mutableStateOf(false)}
        val chosenTable = remember { mutableIntStateOf(-1) }

        val isTableChosen = remember { mutableStateOf(false) }
        val isDateChosen = remember { mutableStateOf(true) }
        val isTimeChosen = remember { mutableStateOf(true) }
        val isReservationError = remember { mutableStateOf(reservationState == ReservationState.Error)}
        val isReservationNotDone = remember { mutableStateOf(reservationState != ReservationState.Success) }
        isReservationNotDone.value = reservationState != ReservationState.Success

        var conditions = listOf(
            isTableChosen.value,
            isDateChosen.value,
            isTimeChosen.value,
            isReservationNotDone.value
        )

        var reservationEnabled by remember {
            mutableStateOf(canMakeReservation(conditions))
        }

        for (condition in conditions.indices) {
            DisposableEffect(conditions[condition]) {
                val observer = {
                    reservationEnabled = canMakeReservation(conditions)
                }
                observer()
                onDispose{}
            }
        }

        if (openTableChoice.value){
            Dialog(
                onDismissRequest = { openTableChoice.value = false }
            ) {
                Card {
                    Column {
                        when (infrastructureState){
                            is InfrastructureState.Loading -> Text(text = "Loading infrastructure")
                            is InfrastructureState.Error -> Text(text = "Error")
                            is InfrastructureState.receivedInfrastructure ->
                                when (tablesState){
                                    is TablesState.Loading -> Text(text = "Loading tables")
                                    is TablesState.Error -> Text(text = "Error")
                                    is TablesState.receivedTables ->
                                        TableList(
                                            infrastructure = infrastructureState.infrastructure,
                                            tables = tablesState.tables,
                                            onTableChosen = {
                                                chosenTableId ->
                                                    chosenTable.value = chosenTableId
                                                    isTableChosen.value = true
                                                    openTableChoice.value = false
                                            }
                                        )
                                }
                        }
                    }
                }

            }
        }
        Button(onClick = { openTableChoice.value = true }) {
            Text(text = "Choose Table")
        }
        Text(text = "Chosen Table: " + if(chosenTable.value < 0) "None" else chosenTable.value.toString())

        if(openDatePicker.value){
            Dialog(
                onDismissRequest = { openDatePicker.value = false },
                properties = DialogProperties(usePlatformDefaultWidth = false)
            ) {
                Card(
                    modifier = Modifier
                        .widthIn(max = 350.dp)
                ) {
                    DatePicker(state = datePickerState)
                    Row(
                        modifier = Modifier
                            .fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceEvenly
                    ) {
                        Button(onClick = { openDatePicker.value = false }) {
                            Text("Confirm")
                        }
                        Button(onClick = { openDatePicker.value = false }) {
                            Text("Cancel")
                        }
                    }

                }

            }
        }

        Button(onClick = { openDatePicker.value = true }) {
            Text(text = "Choose Date")
        }

        if (openTimer.value){
            TimerDialog(
                onDismissRequest = { openTimer.value = false },
                onConfirmation = { openTimer.value = false },
                timerState = timerState,
            )
        }
        Button(onClick = { openTimer.value = true }) {
            Text(text = "Choose Time")
        }
        var dateTimePicked = combineDateTime(datePickerState.selectedDateMillis, timerState.hour, timerState.minute)
        Text(
            text = "Date and time picked: ${dateTimePicked}",
            textAlign = TextAlign.Center
        )

        Button(
            onClick = {
                dateTimePicked = combineDateTime(datePickerState.selectedDateMillis, timerState.hour, timerState.minute)
                val sdf = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")
                sdf.timeZone = TimeZone.getTimeZone("GMT+2")
                val formattedDate = sdf.format(Date(dateTimePicked))
                Log.d("date time", formattedDate.toString())

                onMakeReservation(SentReservation(formattedDate, formattedDate, AuthContext.getUser().id, chosenTable.value))
            },
            enabled = reservationEnabled
        ) {
            Text(text = "Make a Reservation")
        }
        if(!isReservationNotDone.value){
            Text(text = "Reservation created!")

        }
        if(isReservationError.value){
            Text(text = "Error")
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TimerDialog(
    onDismissRequest: () -> Unit,
    onConfirmation: () -> Unit,
    timerState: TimePickerState,
) {
    Dialog(
        onDismissRequest = { onDismissRequest() },
    ) {
        Card(
            modifier = Modifier
                .heightIn(min = 400.dp)
                .widthIn(min = 350.dp)
        ) {
            TimePicker(
                state = timerState,
                modifier = Modifier.padding(16.dp)
            )
            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                Button(onClick = { onConfirmation() }) {
                    Text(text = "Confirm")
                }
                Button(onClick = { onDismissRequest() }) {
                    Text(text = "Cancel")
                }
            }
        }
    }
}

private fun combineDateTime(milliseconds: Long?, hours: Int, minutes: Int): Long {
    val date = milliseconds?.let { Date(it) }

    val calendar = Calendar.getInstance()
    calendar.time = date

    calendar.set(Calendar.HOUR_OF_DAY, hours)
    calendar.set(Calendar.MINUTE, minutes)

    return calendar.timeInMillis
}

fun canMakeReservation(conditions: List<Boolean>): Boolean {
    return conditions.all { it }
}

@Composable
fun TableList(
    infrastructure: Infrastructure,
    tables: List<Table>,
    onTableChosen: (chosenTableId: Int) -> Unit = {},
) {
    var rows = infrastructure.numberOfRows
    var columns = infrastructure.numberOfColumns
    repeat(rows){rowIndex ->
        Row(){
            repeat(columns){ columnIndex ->
                for (table in tables) {
                    if(rowIndex == table.gridRow && columnIndex == table.gridColumn){
                        Box(
                            modifier = Modifier
                                .padding(16.dp)
                        ) {
                            Text(text = "Table "+table.id.toString())
                            TextButton(
                                onClick = { onTableChosen(table.id) },
                                enabled = table.isAvailable
                            ) {
                                Text(text = "Choose")
                            }
                        }
                    }
                }

            }
        }

    }
}
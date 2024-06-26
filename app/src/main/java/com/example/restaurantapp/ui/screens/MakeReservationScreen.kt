package com.example.restaurantapp.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.DatePicker
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.example.restaurantapp.R
import com.example.restaurantapp.data.AuthContext
import com.example.restaurantapp.model.Infrastructure
import com.example.restaurantapp.model.SentReservation
import com.example.restaurantapp.model.Table
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.TimeZone
import kotlin.time.Duration.Companion.milliseconds

@ExperimentalMaterial3Api
@Composable
fun MakeReservationScreen(
    onMakeReservation: (reservation: SentReservation) -> Unit = {},
    reservationState: Any,
    tablesState: TablesState = TablesState.Loading,
    infrastructureState: InfrastructureState = InfrastructureState.Loading,
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = Modifier
            .verticalScroll(rememberScrollState())
            .widthIn(max = 350.dp)
    ) {
        Text(
            text = stringResource(R.string.create_a_reservation_heading),
            style = MaterialTheme.typography.headlineLarge,
        )
        val datePickerStateFrom = rememberDatePickerState(initialSelectedDateMillis = System.currentTimeMillis())
        val datePickerStateTo = rememberDatePickerState(initialSelectedDateMillis = System.currentTimeMillis())
        val timerStateFrom = rememberTimePickerState()
        val timerStateTo = rememberTimePickerState()
        val openTimerFrom = remember { mutableStateOf(false)}
        val openTimerTo = remember { mutableStateOf(false)}
        val openDatePickerFrom = remember { mutableStateOf(false)}
        val openDatePickerTo = remember { mutableStateOf(false)}
        val openTableChoice = remember { mutableStateOf(false)}
        val chosenTable = remember { mutableIntStateOf(-1) }

        val isTableChosen = remember { mutableStateOf(false) }
        val isDateChosen = remember { mutableStateOf(true) }
        val isTimeChosen = remember { mutableStateOf(true) }
        val isReservationError = remember { mutableStateOf(false)}
        isReservationError.value = reservationState == ReservationState.Error
        val isReservationNotDone = remember { mutableStateOf(true) }
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
                            is InfrastructureState.Loading -> Text(text = stringResource(R.string.loading_infrastructure))
                            is InfrastructureState.Error -> Text(text = stringResource(R.string.error))
                            is InfrastructureState.receivedInfrastructure ->
                                when (tablesState){
                                    is TablesState.Loading -> Text(text = stringResource(R.string.loading_tables))
                                    is TablesState.Error -> Text(text = stringResource(R.string.error))
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
            Text(text = stringResource(R.string.choose_table_button))
        }
        Text(text = stringResource(R.string.chosen_table) +
                if(chosenTable.value < 0) stringResource(R.string.chosen_table_none) else chosenTable.value.toString())

        if(openDatePickerFrom.value){
            Dialog(
                onDismissRequest = { openDatePickerFrom.value = false },
                properties = DialogProperties(usePlatformDefaultWidth = false)
            ) {
                Card(
                    modifier = Modifier
                        .widthIn(max = 350.dp)
                ) {
                    DatePicker(state = datePickerStateFrom)
                    Row(
                        modifier = Modifier
                            .fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceEvenly
                    ) {
                        Button(onClick = { openDatePickerFrom.value = false }) {
                            Text(stringResource(R.string.confirm_button))
                        }
                        Button(onClick = { openDatePickerFrom.value = false }) {
                            Text(stringResource(R.string.cancel_button))
                        }
                    }

                }

            }
        }

        Button(onClick = { openDatePickerFrom.value = true }) {
            Text(text = "Choose Date From")
        }
        if(openDatePickerTo.value){
            Dialog(
                onDismissRequest = { openDatePickerTo.value = false },
                properties = DialogProperties(usePlatformDefaultWidth = false)
            ) {
                Card(
                    modifier = Modifier
                        .widthIn(max = 350.dp)
                ) {
                    DatePicker(state = datePickerStateTo)
                    Row(
                        modifier = Modifier
                            .fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceEvenly
                    ) {
                        Button(onClick = { openDatePickerTo.value = false }) {
                            Text(stringResource(R.string.confirm_button))
                        }
                        Button(onClick = { openDatePickerTo.value = false }) {
                            Text(stringResource(R.string.cancel_button))
                        }
                    }

                }

            }
        }

        if (openTimerFrom.value){
            TimerDialog(
                onDismissRequest = { openTimerFrom.value = false },
                onConfirmation = { openTimerFrom.value = false },
                timerState = timerStateFrom,
            )
        }
        Button(onClick = { openTimerFrom.value = true }) {
            Text(text = "Choose Time From")
        }

        Button(onClick = { openDatePickerTo.value = true }) {
            Text(text = "Choose Date To")
        }

        if (openTimerTo.value){
            TimerDialog(
                onDismissRequest = { openTimerTo.value = false },
                onConfirmation = { openTimerTo.value = false },
                timerState = timerStateTo,
            )
        }
        Button(onClick = { openTimerTo.value = true }) {
            Text(text = "Choose Time To")
        }

        var dateTimePickedFrom = combineDateTime(datePickerStateFrom.selectedDateMillis, timerStateFrom.hour, timerStateFrom.minute)
        var dateTimePickedTo = combineDateTime(datePickerStateTo.selectedDateMillis, timerStateTo.hour, timerStateTo.minute)

        var sdf = SimpleDateFormat("HH:mm dd-MM-yyyy")
        var formattedDateFrom = sdf.format(Date(dateTimePickedFrom))
        var formattedDateTo = sdf.format(Date(dateTimePickedTo))

        Text(
            text = "Date and time picked from: " + formattedDateFrom,
            textAlign = TextAlign.Center,
            modifier = Modifier
                .widthIn(max=350.dp)
        )
        Text(
            text = "Date and time picked to: " + formattedDateTo,
            textAlign = TextAlign.Center,
            modifier = Modifier
                .widthIn(max=350.dp)
        )

        Button(
            onClick = {
                dateTimePickedFrom = combineDateTime(datePickerStateFrom.selectedDateMillis, timerStateFrom.hour, timerStateFrom.minute)
                dateTimePickedTo = combineDateTime(datePickerStateFrom.selectedDateMillis, timerStateTo.hour, timerStateTo.minute)

                val sdf = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")
                sdf.timeZone = TimeZone.getTimeZone("GMT+2")

                val formattedDateFrom = sdf.format(Date(dateTimePickedFrom))
                val formattedDateTo = sdf.format(Date(dateTimePickedTo))

                onMakeReservation(SentReservation(formattedDateFrom, formattedDateTo, AuthContext.getUser().id, chosenTable.value))
            },
            enabled = reservationEnabled && dateTimePickedFrom < dateTimePickedTo
        ) {
            Text(text = stringResource(R.string.make_a_reservation_button))
        }
        if(!isReservationNotDone.value){
            Text(text = stringResource(R.string.reservation_created))

        }
        if(isReservationError.value){
            Text(text = stringResource(R.string.error))
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
                    Text(text = stringResource(R.string.confirm_button))
                }
                Button(onClick = { onDismissRequest() }) {
                    Text(text = stringResource(R.string.cancel_button))
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
        Row {
            repeat(columns){ columnIndex ->
                for (table in tables) {
                    if(rowIndex == table.gridRow && columnIndex == table.gridColumn){
                        Box(
                            modifier = Modifier
                                .padding(16.dp)
                        ) {
                            Column {
                                Text(text = stringResource(R.string.table)+table.id.toString())
                                Text(text = stringResource(R.string.seats) + table.numberOfSeats.toString())
                                TextButton(
                                    onClick = { onTableChosen(table.id) },
                                    enabled = table.isAvailable
                                ) {
                                    Text(text = stringResource(R.string.choose_button))
                                }
                            }

                        }
                    }
                }

            }
        }

    }
}
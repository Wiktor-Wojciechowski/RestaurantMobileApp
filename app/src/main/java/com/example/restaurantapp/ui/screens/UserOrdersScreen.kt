package com.example.restaurantapp.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
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
fun UserOrdersScreen(
    userOrdersState: UserOrdersState,
    onSetReadyToPay: (orderId: Int) -> Unit = {}
){
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .verticalScroll(rememberScrollState())
    ){
        when(userOrdersState){
            is UserOrdersState.Loading -> Text(stringResource(R.string.loading))
            is UserOrdersState.Error -> Text(stringResource(R.string.error))
            is UserOrdersState.receivedOrders ->
                Column(
                    modifier = Modifier
                        .widthIn(max = 350.dp)
                ) {
                    Text(
                        text = stringResource(R.string.your_orders_heading),
                        modifier = Modifier
                            .fillMaxWidth(),
                        style = MaterialTheme.typography.headlineLarge,
                    )
                    var orders = userOrdersState.orders
                    for (order in orders) {
                        Card (
                            modifier = Modifier
                                .padding(4.dp)
                                .wrapContentHeight()
                                .defaultMinSize(350.dp, 100.dp)
                                .widthIn(max = 350.dp)

                        ){
                            Column(
                                modifier = Modifier
                                    .padding(8.dp)
                            ) {
                                Text(stringResource(R.string.dishes))
                                order.dishModels.forEach { dish ->
                                    Text(text = dish.name)
                                }
                                var orderStatusMessage = ""
                                when (order.status){
                                    1 -> orderStatusMessage =
                                        stringResource(R.string.order_status_ready)
                                    2 -> orderStatusMessage =
                                        stringResource(R.string.order_status_being_prepared)
                                    3 -> orderStatusMessage =
                                        stringResource(R.string.order_status_ready_to_pay)
                                    4 -> orderStatusMessage =
                                        stringResource(R.string.order_status_paid)
                                }
                                Text(stringResource(R.string.table_number) +order.tableModelId)
                                Text(stringResource(R.string.status) + orderStatusMessage)
                                Text(stringResource(R.string.price) +order.price)
                                Row(
                                    horizontalArrangement = Arrangement.Center,
                                    modifier = Modifier
                                        .fillMaxWidth()
                                ) {
                                    Button(
                                        onClick = {
                                            onSetReadyToPay(order.id)
                                        },
                                        enabled = order.status == 1
                                    ) {
                                        Text(text = stringResource(R.string.ready_to_pay_button))
                                    }
                                }

                            }
                        }
                    }
                }
        }
    }
}

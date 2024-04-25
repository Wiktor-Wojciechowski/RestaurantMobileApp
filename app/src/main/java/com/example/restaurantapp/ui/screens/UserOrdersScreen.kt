package com.example.restaurantapp.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
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
import androidx.compose.ui.semantics.Role.Companion.Button
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp

@Composable
fun UserOrdersScreen(
    userOrdersState: UserOrdersState,
    onCancelOrder: () -> Unit = {},
    viewModel: UserOrdersViewModel
){
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .verticalScroll(rememberScrollState())
    ){
        when(userOrdersState){
            is UserOrdersState.Loading -> Text("Loading")
            is UserOrdersState.Error -> Text(text = "Error")
            is UserOrdersState.receivedOrders ->
                Column(
                    modifier = Modifier
                        .widthIn(max = 350.dp)
                ) {
                    Text(
                        text = "Your Orders: ",
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
                            Text("Dish Models: "+order.dishModels.toString())
                            Text("Table Id: "+order.tableModelId)
                            Text("Table Model: "+order.tableModel.toString())
                            Text("Status: "+order.status)
                            Text("Price: "+order.price)
                            /*
                            if(order.status == 2){
                                Button(onClick = { viewModel.cancelOrder(order.id) }) {
                                    Text("Cancel Order")
                                }
                            }

                             */
                        }
                    }
                }
        }
    }
}

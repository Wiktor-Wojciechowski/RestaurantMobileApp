package com.example.restaurantapp.ui.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.restaurantapp.R


@Composable
fun OrderScreen(
    orderState: OrderState,
    contentPadding: PaddingValues = PaddingValues(0.dp)
) {
    Column() {


        when (orderState) {
            is OrderState.Loading -> Text(stringResource(R.string.loading))
            is OrderState.Error -> Text(stringResource(R.string.error))
            is OrderState.receivedDishes -> Column {
                orderState.dishes.forEach { dish ->
                    Text(dish.name)
                }
            }/*LazyVerticalGrid(
            columns = GridCells.Adaptive(0.dp),
            contentPadding = contentPadding
        ){
            items(orderState.dishes){ dish -> Text(dish.id.toString())}
        }
        */
        }


        Button(onClick = {}) {
            Text(text = stringResource(R.string.orderButton))
        }
    }
}
package com.example.restaurantapp.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.Checkbox
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.restaurantapp.R
import com.example.restaurantapp.model.Dish

var fakeDishes = listOf(
    Dish(0,"pierogtgi","string", 1, true),
    Dish(0,"kapusniak","string", 2, true),
    Dish(0,"spaghetti","string", 3, true),
    Dish(0,"pelmeni","string", 2, false),
    Dish(0,"nalesniki","string", 1, true),
    Dish(0,"rosol","string", 4, true),
    Dish(0,"pierogi","string", 1, true),
    Dish(0,"kapusniak","string", 2, true),
    Dish(0,"spaghetti","string", 3, true),
    Dish(0,"pelmeni","string", 2, false),
    Dish(0,"nalesniki","string", 1, true),
    Dish(0,"rosol","string", 4, true),

)

@Composable
fun OrderScreen(
    orderState: OrderState,
    onGoToTableChoice: () -> Unit = {},
    onReturn: () -> Unit = {},
    viewModel: OrderViewModel
) {

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = Modifier
            .verticalScroll(rememberScrollState())
    ) {
        /*
        when (orderState) {
            is OrderState.Loading -> Text(stringResource(R.string.loading))
            is OrderState.Error -> Text(stringResource(R.string.error))
            is OrderState.receivedDishes -> Column {
                orderState.dishes.forEach { dish ->
                    Card {
                        Text(dish.name)
                    }

                }
            }
        }

         */
        fakeDishes.forEach{ dish ->
            Card(
                modifier = Modifier
                    .size(width = 240.dp, height = 100.dp)
                    .padding(4.dp)

            ) {
                Text(
                    dish.name,
                    modifier = Modifier
                        .padding(8.dp)
                )
                Text(dish.description)
                Text(dish.availability.toString())
                Text(dish.price.toString())
                //Checkbox(checked = false, onCheckedChange = {onDishChecked(viewModel, dish.id)})
                Row(){
                    Button(onClick = { /*TODO*/ }) {
                        
                    }
                    Text(text = "")
                    Button(onClick = { /*TODO*/ }) {
                        
                    }
                }
            }

        }
        Button(onClick = {onGoToTableChoice()}) {
            Text(text = stringResource(R.string.goToTableChoiceButton))
        }

        Button(onClick = {onReturn()}) {
            Text(text = stringResource(R.string.return_button))
        }
    }
}

fun addDishToCart(viewmodel: OrderViewModel, dishID:Int) {
    viewmodel.addDishToCart(dishID)
}
fun removeDishFromCart(viewmodel: OrderViewModel, dishID:Int){
    viewmodel.removeDishFromCart(dishID)
}

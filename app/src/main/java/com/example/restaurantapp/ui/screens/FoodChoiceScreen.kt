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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.restaurantapp.R
import com.example.restaurantapp.model.Dish

var fakeDishes = listOf(
    Dish(0,"pierogtgi","string", 1, true),
    Dish(1,"kapusniak","string", 2, true),
    Dish(2,"spaghetti","string", 3, true),
    Dish(3,"pelmeni","string", 2, false),
    Dish(4,"nalesniki","string string string string string string string string string string string string string string string string string string string ", 1, true),
    Dish(5,"rosol","string", 4, true),
    Dish(6,"pierogi","string", 1, true),
    Dish(7,"kapusniak","string", 2, true),
    Dish(8,"spaghetti","string", 3, true),
    Dish(9,"pelmeni","string", 2, false),
    Dish(10,"nalesniki","string", 1, true),
    Dish(11,"rosol","string", 4, true),

)

@Composable
fun FoodChoiceScreen(
    foodState: FoodState,
    onGoToTableChoice: () -> Unit = {},
    onReturn: () -> Unit = {},
    viewModel: FoodChoiceViewModel,
    orderViewModel: OrderViewModel
) {
    var isCartEmpty by remember {mutableStateOf(true)}

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .verticalScroll(rememberScrollState())
            .widthIn(max = 350.dp)
    ) {
        Text(
            text = stringResource(R.string.choose_your_dishes_heading),
            style = MaterialTheme.typography.headlineLarge,
        )

        when (foodState) {
            is FoodState.Loading -> Text(stringResource(R.string.loading))
            is FoodState.Error -> Text(stringResource(R.string.error))
            is FoodState.receivedDishes -> Column {
                foodState.dishes.forEach { dish ->
                    Card(
                        modifier = Modifier
                            .padding(4.dp)
                            .wrapContentHeight()
                            .defaultMinSize(350.dp, 100.dp)
                            .widthIn(max = 350.dp)

                        ) {
                        Column(
                            modifier = Modifier
                                .padding(8.dp),
                        ) {
                            Text(
                                dish.name,
                                modifier = Modifier
                                    .padding(8.dp),
                                fontSize = 20.sp
                            )
                            Text(dish.description)
                            Text(stringResource(R.string.dish_available) +
                                    if(dish.availability) stringResource(R.string.yes) else stringResource(R.string.no)
                            )
                            Text(stringResource(R.string.dish_price) + dish.price.toString())
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.Center,
                                modifier = Modifier
                                    .fillMaxWidth()
                            ){
                                var amount: String by remember{mutableStateOf(viewModel.cart.count{it == dish.id}.toString())}
                                Button(
                                    onClick = {
                                        viewModel.removeDishFromCart(dish.id)
                                        amount = viewModel.cart.count{it == dish.id}.toString()
                                        isCartEmpty = viewModel.cart.isEmpty()
                                    },
                                    enabled = dish.availability
                                ) {
                                    Text(text = "-")
                                }
                                Text(
                                    modifier = Modifier.padding(16.dp),
                                    text = amount
                                )
                                Button(
                                    onClick = {
                                        viewModel.addDishToCart(dish.id)
                                        amount = viewModel.cart.count{it == dish.id}.toString()
                                        isCartEmpty = viewModel.cart.isEmpty()
                                    },
                                    enabled = dish.availability
                                ) {
                                    Text(text = "+")
                                }
                            }
                        }

                    }

                }
            }
        }

        Button(
            onClick = {
                orderViewModel.setCart(viewModel.cart)
                onGoToTableChoice()
            },
            enabled = !isCartEmpty
        ) {
            Text(text = stringResource(R.string.goToTableChoiceButton))
        }

        Button(onClick = {onReturn()}) {
            Text(text = stringResource(R.string.return_button))
        }
    }
}

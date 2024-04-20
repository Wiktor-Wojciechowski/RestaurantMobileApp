package com.example.restaurantapp.ui.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable

@Composable
fun FinalizeOrderScreen(
    orderVM: OrderViewModel,
    tableVM: TableChoiceViewModel,
    onGoToHome: () -> Unit = {}
){
    Column(){
        Column(){
            //chosen dishes
        }
        Card(){
            //chosen table
        }
        Button(onClick = { onGoToHome() }) {
            Text(text = "Return Home")
        }
    }

}
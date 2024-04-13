package com.example.restaurantapp.ui.screens

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.restaurantapp.RestaurantApplication
import com.example.restaurantapp.data.RestaurantRepository
import com.example.restaurantapp.model.Dish
import com.example.restaurantapp.model.Table
import kotlinx.coroutines.launch

interface OrderState {
    data class receivedDishes(val dishes: List<Dish>) : OrderState
    object Loading : OrderState
    object Error : OrderState
}

class OrderViewModel(private val repository: RestaurantRepository): ViewModel() {
    var orderState: OrderState by mutableStateOf(OrderState.Loading)
        private set

    var cart = mutableListOf<Int>()
    init {
        //getDishes()
    }

    fun getDishes(){
        viewModelScope.launch {
            Log.d("order_init", "init")
            orderState = OrderState.Loading
            try {
                orderState = OrderState.receivedDishes(repository.getDishes())

            }catch (e:Exception){
                orderState = OrderState.Error
                Log.d("dishException", e.toString())
            }
        }
    }
    fun addDishToCart(dishID: Int){
        Log.d("addDish","dish added")
    }
    fun removeDishFromCart(dishID: Int){
        Log.d("remDish","dish removed")
    }

    companion object {
        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val application = (this[APPLICATION_KEY] as RestaurantApplication)
                val restaurantRepository = application.container.restaurantRepository
                OrderViewModel(repository = restaurantRepository)
            }
        }
    }
}

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
import kotlinx.coroutines.launch

interface FoodState {
    data class receivedDishes(val dishes: List<Dish>) : FoodState
    object Loading : FoodState
    object Error : FoodState
}

class FoodChoiceViewModel(private val repository: RestaurantRepository): ViewModel() {
    var foodState: FoodState by mutableStateOf(FoodState.Loading)
        private set

    var cart = mutableListOf<Int>()
    init {
        getDishes()
    }

    fun getDishes(){
        viewModelScope.launch {
            Log.d("order_init", "init")
            foodState = FoodState.Loading
            try {
                foodState = FoodState.receivedDishes(repository.getDishes())

            }catch (e:Exception){
                foodState = FoodState.Error
                Log.d("dishException", e.toString())
            }
        }
    }
    fun addDishToCart(dishID: Int){
        cart.add(dishID)
    }
    fun removeDishFromCart(dishID: Int){
        cart.remove(dishID)
    }

    companion object {
        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val application = (this[APPLICATION_KEY] as RestaurantApplication)
                val restaurantRepository = application.container.restaurantRepository
                FoodChoiceViewModel(repository = restaurantRepository)
            }
        }
    }
}

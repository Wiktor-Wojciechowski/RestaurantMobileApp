package com.example.restaurantapp.ui.screens

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.restaurantapp.RestaurantApplication
import com.example.restaurantapp.data.AuthContext
import com.example.restaurantapp.data.RestaurantRepository
import com.example.restaurantapp.model.Order
import com.example.restaurantapp.model.Table
import kotlinx.coroutines.launch
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

interface OrderState {
    object Success: OrderState
    object Loading : OrderState
    object Error : OrderState
}

class OrderViewModel(private val repository: RestaurantRepository): ViewModel(){
    private lateinit var cart: List<Int>
    private var chosenTable: Table? = null
    var orderState: OrderState by mutableStateOf(OrderState.Loading)
        private set

    fun setCart(new_cart: List<Int>){
        cart = new_cart
    }
    fun getCart(): List<Int> {
        return cart
    }
    fun setTable(new_table: Table){
        chosenTable = new_table
    }
    fun getTable(): Table? {
        return chosenTable
    }
    fun sendOrder(){
        var new_order = Order (
            dishModelsId = getCart(),
            tableModelId = getTable()?.id ?: -1,
            identityUserId = AuthContext.getUser().id
        )
        viewModelScope.launch {
            try{
                var call = repository.sendOrder("Bearer "+AuthContext.getUser().token, new_order)
                call.enqueue(object: Callback<ResponseBody> {
                    override fun onResponse(
                        call: Call<ResponseBody>,
                        response: Response<ResponseBody>
                    ) {
                        if(response.isSuccessful) {
                            Log.d("orderResponse", response.body()?.string() ?: "noresponse")
                            orderState = OrderState.Success
                        }else{
                            Log.d("orderResponseErr", response.toString())
                            orderState = OrderState.Error
                        }
                    }

                    override fun onFailure(
                        call: Call<ResponseBody>,
                        t: Throwable
                    ) {
                        Log.d("orderResponseF", t.message ?: "null throwable")
                        orderState = OrderState.Error
                    }
                })
            } catch(e:Exception){
                Log.d("OrderError", e.message.toString())
            }
        }
    }
    companion object {
        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val application = (this[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY] as RestaurantApplication)
                val restaurantRepository = application.container.restaurantRepository
                OrderViewModel(repository = restaurantRepository)
            }
        }
    }
}
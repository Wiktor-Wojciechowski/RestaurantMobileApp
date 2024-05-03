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
import com.example.restaurantapp.model.OrderStatus
import com.example.restaurantapp.model.ReceivedOrder
import kotlinx.coroutines.launch
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

interface UserOrdersState{
    data class receivedOrders(val orders: List<ReceivedOrder>): UserOrdersState
    object Loading: UserOrdersState
    object Error: UserOrdersState
}

class UserOrdersViewModel(private val repository: RestaurantRepository) : ViewModel() {
    var userOrdersState: UserOrdersState by mutableStateOf(UserOrdersState.Loading)
        private set

    init {
        getOrders()
    }
    fun getOrders(){
        viewModelScope.launch {
            Log.d("order_init", "init")

            try {
                userOrdersState = UserOrdersState.receivedOrders(repository.getOrders(AuthContext.getUser().id, "Bearer "+AuthContext.getUser().token))

            }catch (e:Exception){
                userOrdersState = UserOrdersState.Error
                Log.d("dishException", e.toString())
            }
        }
    }
    fun setOrderReadyToPay(orderId: Int){
        viewModelScope.launch {
            try {
                var call = repository.setOrderReadyToPay(orderId = orderId, token = "Bearer " + AuthContext.getUser().token)
                call.enqueue(object: Callback<ResponseBody>{
                    override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                        Log.d("setorderfailure", t.toString())
                    }
                    override fun onResponse(
                        call: Call<ResponseBody>,
                        response: Response<ResponseBody>
                    ) {
                        if(!response.isSuccessful) {
                            Log.d("setorderresponsefailure", response.message())
                        }else {
                            getOrders()
                            Log.d("order rtp", "success")
                        }
                    }

                })
            }catch (e:Exception){
                Log.d("SetOrderRTPException", e.toString())
            }
        }
    }

    companion object {
        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val application = (this[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY] as RestaurantApplication)
                val restaurantRepository = application.container.restaurantRepository
                UserOrdersViewModel(repository = restaurantRepository)
            }
        }
    }
}
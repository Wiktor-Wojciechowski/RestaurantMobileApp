package com.example.restaurantapp.data

import com.example.restaurantapp.network.LoginParams
import com.example.restaurantapp.network.RegisterParams
import com.example.restaurantapp.network.RestaurantApiService
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback

class RestaurantRepository (private val restaurantApiService: RestaurantApiService){
    suspend fun registerUser(body: RegisterParams): Call<ResponseBody>{
        return restaurantApiService.registerUser(body)
    }

    suspend fun loginUser(body: LoginParams): Call<ResponseBody>{
        return restaurantApiService.loginUser(body)
    }
}
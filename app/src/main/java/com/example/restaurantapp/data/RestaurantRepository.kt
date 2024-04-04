package com.example.restaurantapp.data

import com.example.restaurantapp.model.Dish
import com.example.restaurantapp.model.LoginParams
import com.example.restaurantapp.model.RegisterParams
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

    suspend fun getDishes(): List<Dish>{
        return restaurantApiService.getDishes()
    }

}
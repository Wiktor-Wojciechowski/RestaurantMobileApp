package com.example.restaurantapp.data

import com.example.restaurantapp.model.Dish
import com.example.restaurantapp.model.Infrastructure
import com.example.restaurantapp.model.LoginParams
import com.example.restaurantapp.model.Order
import com.example.restaurantapp.model.ReceivedOrder
import com.example.restaurantapp.model.ReceivedReservation
import com.example.restaurantapp.model.RegisterParams
import com.example.restaurantapp.model.SentReservation
import com.example.restaurantapp.model.Table
import com.example.restaurantapp.network.RestaurantApiService
import okhttp3.ResponseBody
import retrofit2.Call

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

    suspend fun getTables(): List<Table>{
        return restaurantApiService.getTables()
    }

    suspend fun getInfrastructure(): Infrastructure {
        return restaurantApiService.getInfrastructure()
    }

    fun sendOrder(token:String, body: Order): Call<ResponseBody>{
        return restaurantApiService.sendOrder(token, body)
    }

    suspend fun getOrders(userId: String, token:String): List<ReceivedOrder>/*Call<ResponseBody>*/{
        return restaurantApiService.getOrders(userId, token)

    }

    fun makeReservation(token:String, body: SentReservation): Call<ResponseBody>{
        return restaurantApiService.makeReservation(token, body)
    }

    suspend fun getUserReservations(token: String, userId: String): List<ReceivedReservation>{
        return restaurantApiService.getUserReservations(userId, token)
    }

}
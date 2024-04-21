package com.example.restaurantapp.network

import com.example.restaurantapp.model.Dish
import com.example.restaurantapp.model.Infrastructure
import com.example.restaurantapp.model.LoginParams
import com.example.restaurantapp.model.Order
import com.example.restaurantapp.model.RegisterParams
import com.example.restaurantapp.model.Table
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Headers
import retrofit2.http.POST


interface RestaurantApiService {
    @Headers("Content-Type:application/json")
    @POST("register-user")
    //make this work with suspend modifier
    fun registerUser(@Body body: RegisterParams): Call<ResponseBody>

    @Headers("Content-Type:application/json")
    @POST("login-user")
    fun loginUser(@Body body: LoginParams): Call<ResponseBody>

    @GET("api/dish")
    suspend fun getDishes(): List<Dish>

    @GET("api/table")
    suspend fun getTables(): List<Table>

    @GET("api/table/get-infrastructure")
    suspend fun getInfrastructure(): Infrastructure

    @POST("api/order")
    fun sendOrder(@Header("Authorization") token: String, @Body body: Order): Call<ResponseBody>

}

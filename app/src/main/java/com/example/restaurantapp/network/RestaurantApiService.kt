package com.example.restaurantapp.network

import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory
import retrofit2.http.Body
import retrofit2.http.Headers
import retrofit2.http.POST

data class RegisterParams(
    val username: String,
    val email: String,
    val password: String,
    val age: String,
)
data class LoginParams(
    val username: String,
    val password: String
)

interface RestaurantApiService {
    @Headers("Content-Type:application/json")
    @POST("post")
    //make this work with suspend modifier
    fun registerUser(@Body body: RegisterParams ): Call<ResponseBody>

    @Headers("Content-Type:application/json")
    @POST("login-worker")
    fun loginUser(@Body body: LoginParams): Call<ResponseBody>
}

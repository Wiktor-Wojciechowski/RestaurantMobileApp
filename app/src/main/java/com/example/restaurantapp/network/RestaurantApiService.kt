package com.example.restaurantapp.network

import retrofit2.Retrofit
import retrofit2.converter.scalars.ScalarsConverterFactory
import retrofit2.http.Body
import retrofit2.http.Headers
import retrofit2.http.POST

private const val BASE_URL = "https://restaurantapi20240224142603.azurewebsites.net/"

private val retrofit = Retrofit.Builder()
    .addConverterFactory(ScalarsConverterFactory.create())
    .baseUrl(BASE_URL)
    .build()

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
    @POST
    fun register_user(@Body body: RegisterParams )



    @Headers("Content-Type:application/json")
    @POST
    fun login_user(@Body body: LoginParams){

    }
}
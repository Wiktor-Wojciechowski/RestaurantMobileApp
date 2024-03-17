package com.example.restaurantapp.data

import com.example.restaurantapp.network.LoginParams
import com.example.restaurantapp.network.RegisterParams
import com.example.restaurantapp.network.RestaurantApiService

class RestaurantRepository (
    private val restaurantApiService: RestaurantApiService
) : RestaurantApiService {
    override suspend fun registerUser(body: RegisterParams): String = restaurantApiService.registerUser(body)
    override suspend fun login_user(body: LoginParams) = restaurantApiService.login_user(body)
}
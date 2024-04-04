package com.example.restaurantapp.model

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
data class Dish(
    val id: Int,
    val name: String,
    val description: String,
    val price: Int,
    val availability: Boolean
)
data class Order(
    val price: Int,
    val tableModelId: Int,
    val dishModelsId: List<Int>,
    val identityUserId: String
)
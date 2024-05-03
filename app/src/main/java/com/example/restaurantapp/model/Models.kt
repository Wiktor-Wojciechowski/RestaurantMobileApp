package com.example.restaurantapp.model

import java.util.Date

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
data class User(
    val id: String,
    val token: String
)
data class Dish(
    val id: Int,
    val name: String,
    val description: String,
    val price: Int,
    val availability: Boolean
)

data class ReceivedDish(
    val id: Int,
    val name: String,
    val description: String,
    val price: Int,
    val availability: Boolean,
    val orders: Any
)
data class Infrastructure(
    val numberOfRows: Int,
    val numberOfColumns: Int
)
data class Table(
    val id: Int,
    val isAvailable: Boolean,
    val numberOfSeats: Int,
    val gridRow: Int,
    val gridColumn: Int
)

data class Order(
    //val price: Int,
    val tableModelId: Int,
    val dishModelsId: List<Int>,
    val identityUserId: String
)

data class ReceivedOrder(
    val id: Int,
    val status: Int,
    val price: Int,
    val tableModelId: Int,
    val tableModel: ReceivedTable,
    val dishModels: List<ReceivedDish>,
    val identityUserId: String,
    val identityUserModel: Any
)

data class OrderStatus(
    val status: Int
)

data class ReceivedTable(
    val id: Int,
    val isAvailable: Boolean,
    val numberOfSeats: Int,
    val gridRow: Int,
    val gridColumn: Int,
    val orderModels: Any,
    val reservations: Any
)

data class SentReservation(
    val from: String,
    val to: String,
    val identityUserId: String,
    val tableModelId: Int
)

data class ReceivedReservation(
    val from: String,
    val to: String,
    val identityUserId: String,
    val tableModelId: Int
)
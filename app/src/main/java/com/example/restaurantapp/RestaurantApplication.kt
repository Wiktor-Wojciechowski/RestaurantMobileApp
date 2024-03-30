package com.example.restaurantapp

import android.app.Application
import com.example.restaurantapp.data.AppContainer
import com.example.restaurantapp.data.DefaultAppContainer

class RestaurantApplication: Application() {
    lateinit var container: AppContainer
    override fun onCreate() {
        super.onCreate()
        container = DefaultAppContainer()
    }
}
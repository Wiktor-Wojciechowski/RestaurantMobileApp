package com.example.restaurantapp.ui.screens

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.restaurantapp.network.RegisterParams
import com.example.restaurantapp.network.RestaurantApi
import kotlinx.coroutines.launch

class RegisterViewModel:ViewModel() {
    fun register(params: RegisterParams){
        viewModelScope.launch {
            try{
                Log.d("RegisterResponse", RestaurantApi.retrofitService.registerUser(params))
            } catch(e:Exception){
                Log.d("RegisterError", e.message.toString())
            }
        }
    }
}
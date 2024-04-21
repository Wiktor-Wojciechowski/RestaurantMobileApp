package com.example.restaurantapp.data

import com.example.restaurantapp.model.User

object AuthContext {
    private lateinit var user: User

    fun setUser(new_user: User){
        user = new_user
    }
    fun getUser(): User{
        return user
    }

}
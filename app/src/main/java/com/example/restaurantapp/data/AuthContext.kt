package com.example.restaurantapp.data

object AuthContext {
    private var token: String = ""
    fun setToken(newToken: String){
        token = newToken
    }
    fun getToken(): String {
        return token
    }
    fun removeToken() {
        token = ""
    }
}
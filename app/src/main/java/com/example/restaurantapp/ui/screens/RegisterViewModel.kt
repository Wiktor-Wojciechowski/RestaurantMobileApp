package com.example.restaurantapp.ui.screens

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.restaurantapp.data.RestaurantRepository
import com.example.restaurantapp.network.RegisterParams
import kotlinx.coroutines.launch
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class RegisterViewModel(val repository: RestaurantRepository):ViewModel() {
    fun registerUser(params: RegisterParams){
        viewModelScope.launch {
            try{
                var call = repository.registerUser(params)
                call.enqueue(object: Callback<ResponseBody> {
                    override fun onResponse(
                        call: Call<ResponseBody>,
                        response: Response<ResponseBody>
                    ) {
                        val data = response.body()?.string()
                        Log.d("GetResponse", data ?: "null")
                    }

                    override fun onFailure(
                        call: Call<ResponseBody>,
                        t: Throwable
                    ) {
                        Log.d("GetResponseT", t.message ?: "null throwable")
                    }
                })
            } catch(e:Exception){
                Log.d("RegisterError", e.message.toString())
            }
        }
    }
}
package com.example.restaurantapp.ui.screens

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavController
import com.example.restaurantapp.RestaurantScreen
import com.example.restaurantapp.data.RestaurantRepository
import com.example.restaurantapp.network.LoginParams
import kotlinx.coroutines.launch
import okhttp3.ResponseBody
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class LoginViewModel(val repository: RestaurantRepository, val navController: NavController): ViewModel() {
    fun loginUser(params: LoginParams){
        viewModelScope.launch {
            try {
                var call = repository.loginUser(params)
                call.enqueue(object: Callback<ResponseBody>{
                    override fun onResponse(
                        call: Call<ResponseBody>,
                        response: Response<ResponseBody>
                    ) {
                        if(response.isSuccessful) {
                            var jsonString = response.body()?.string()
                            val json = JSONObject(jsonString)
                            val token = json.get("token")
                            Log.d("responseS", token.toString() ?: "noresponse")
                            navController.navigate(RestaurantScreen.Home.name)
                        }else{
                            Log.d("responseE", response.toString())
                        }
                    }

                    override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                        Log.d("LoginResponseT", t.message ?: "null throwable")
                    }
                })
            } catch(e:Exception){
                Log.d("LoginError", e.message.toString())
            }
        }
    }
}
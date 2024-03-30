package com.example.restaurantapp.ui.screens

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import androidx.navigation.NavController
import com.example.restaurantapp.RestaurantApplication
import com.example.restaurantapp.ui.RestaurantScreen
import com.example.restaurantapp.data.RestaurantRepository
import com.example.restaurantapp.network.RegisterParams
import kotlinx.coroutines.launch
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class RegisterViewModel(val repository: RestaurantRepository/*, val navController: NavController*/):ViewModel() {
    fun registerUser(params: RegisterParams){
        viewModelScope.launch {
            try{
                var call = repository.registerUser(params)
                call.enqueue(object: Callback<ResponseBody> {
                    override fun onResponse(
                        call: Call<ResponseBody>,
                        response: Response<ResponseBody>
                    ) {
                        if(response.isSuccessful) {
                            Log.d("responseS", response.body()?.string() ?: "noresponse")
                            //navController.navigate(RestaurantScreen.Login.name)
                        }else{
                            Log.d("responseE", response.toString())
                        }
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
    companion object {
        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val application = (this[APPLICATION_KEY] as RestaurantApplication)
                val restaurantRepository = application.container.restaurantRepository
                LoginViewModel(repository = restaurantRepository)
            }
        }
    }
}
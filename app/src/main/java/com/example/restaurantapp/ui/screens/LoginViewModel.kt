package com.example.restaurantapp.ui.screens

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
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
import com.example.restaurantapp.network.Dish
import com.example.restaurantapp.network.LoginParams
import kotlinx.coroutines.launch
import okhttp3.ResponseBody
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

interface LoginState {
    object Success : LoginState
    object Loading : LoginState
    object Error : LoginState
}
class LoginViewModel(val repository: RestaurantRepository/*, val navController: NavController*/): ViewModel() {
    var loginState: LoginState by mutableStateOf(LoginState.Loading)
        private set
    fun loginUser(params: LoginParams){
        viewModelScope.launch {
            try {
                var call = repository.loginUser(params)
                call.enqueue(object: Callback<ResponseBody>{
                    override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                        Log.d("LoginResponseT", t.message ?: "null throwable")
                    }
                    override fun onResponse(
                        call: Call<ResponseBody>,
                        response: Response<ResponseBody>
                    ) {
                        if(!response.isSuccessful) {
                            Log.d("responseE", response.toString())
                            loginState = LoginState.Error

                        }else{
                            var jsonString = response.body()?.string()
                            val json = JSONObject(jsonString)
                            val token = json.get("token")
                            Log.d("responseS", token.toString() ?: "noresponse")
                            //navController.navigate(RestaurantScreen.Home.name)
                            loginState = LoginState.Success
                        }
                    }

                })
            } catch(e:Exception){
                Log.d("LoginError", e.message.toString())
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
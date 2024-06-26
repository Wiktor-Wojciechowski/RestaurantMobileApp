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
import com.example.restaurantapp.RestaurantApplication
import com.example.restaurantapp.data.AuthContext
import com.example.restaurantapp.data.RestaurantRepository
import com.example.restaurantapp.model.LoginParams
import com.example.restaurantapp.model.User
import kotlinx.coroutines.launch
import okhttp3.ResponseBody
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

interface LoginState {
    object Init : LoginState
    object Success : LoginState
    object Loading : LoginState
    data class Error(val errorMessage:String = "") : LoginState

}
class LoginViewModel(val repository: RestaurantRepository): ViewModel() {
    var loginState: LoginState by mutableStateOf(LoginState.Init)
        private set
    fun loginUser(params: LoginParams){
        viewModelScope.launch {
            try {
                loginState = LoginState.Loading
                var call = repository.loginUser(params)
                call.enqueue(object: Callback<ResponseBody>{
                    override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                        Log.d("LoginResponseT", t.message ?: "null throwable")
                        loginState = LoginState.Error("An error has occured. Try again")
                    }
                    override fun onResponse(
                        call: Call<ResponseBody>,
                        response: Response<ResponseBody>
                    ) {
                        if(!response.isSuccessful) {
                            Log.d("responseE", response.body().toString())
                            if(response.code() == 401){
                                loginState = LoginState.Error("Wrong username or password")
                            }else{
                                loginState = LoginState.Error("An error has occured")
                            }

                        }else{
                            var jsonString = response.body()?.string()
                            if (jsonString.isNullOrEmpty()) {
                                loginState = LoginState.Error("Response body is empty")
                            } else {
                                val json = JSONObject(jsonString)
                                val token = json.getString("token")
                                val id = json.getString("userId")
                                Log.d("responseS", token)
                                AuthContext.setUser(User(id, token))
                                loginState = LoginState.Success
                            }
                        }
                    }

                })
            } catch(e:Exception){
                Log.d("LoginError", e.message.toString())
                loginState = LoginState.Error(e.message.toString())
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
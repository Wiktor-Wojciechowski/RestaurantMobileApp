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
import com.example.restaurantapp.data.RestaurantRepository
import com.example.restaurantapp.model.Dish
import com.example.restaurantapp.model.RegisterParams
import kotlinx.coroutines.launch
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
interface RegistrationState {
    object Success : RegistrationState
    object Loading : RegistrationState
    object Error : RegistrationState
    object Init : RegistrationState
}
class RegisterViewModel(val repository: RestaurantRepository):ViewModel() {
    var registrationState: RegistrationState by mutableStateOf(RegistrationState.Init)
        private set
    fun registerUser(params: RegisterParams){
        viewModelScope.launch {
            try{
                registrationState = RegistrationState.Loading
                var call = repository.registerUser(params)
                call.enqueue(object: Callback<ResponseBody> {
                    override fun onResponse(
                        call: Call<ResponseBody>,
                        response: Response<ResponseBody>
                    ) {
                        if(response.isSuccessful) {
                            Log.d("responseS", response.body()?.string() ?: "noresponse")
                            registrationState = RegistrationState.Success
                        }else{
                            Log.d("responseE", response.toString())
                            registrationState = RegistrationState.Error
                        }
                    }

                    override fun onFailure(
                        call: Call<ResponseBody>,
                        t: Throwable
                    ) {
                        Log.d("GetResponseT", t.message ?: "null throwable")
                        registrationState = RegistrationState.Error
                    }
                })
            } catch(e:Exception){
                Log.d("RegisterError", e.message.toString())
                registrationState = RegistrationState.Error
            }
        }
    }
    companion object {
        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val application = (this[APPLICATION_KEY] as RestaurantApplication)
                val restaurantRepository = application.container.restaurantRepository
                RegisterViewModel(repository = restaurantRepository)
            }
        }
    }
}
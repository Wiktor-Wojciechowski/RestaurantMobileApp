package com.example.restaurantapp

import androidx.compose.foundation.layout.Box
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.restaurantapp.data.RestaurantRepository
import com.example.restaurantapp.network.RegisterParams
import com.example.restaurantapp.network.RestaurantApiService
import com.example.restaurantapp.ui.screens.LogInScreen
import com.example.restaurantapp.ui.screens.RegisterScreen
import com.example.restaurantapp.ui.screens.RegisterViewModel
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

enum class RestaurantScreen {
    Login,
    Register
}

private const val BASE_URL = "https://httpbin.org/"

fun createRetrofit(): Retrofit{
    return Retrofit
        .Builder()
        .addConverterFactory(GsonConverterFactory.create())
        .baseUrl(BASE_URL)
        .build()
}
fun createApiService(
    retrofit: Retrofit
): RestaurantApiService{
    return retrofit
        .create(RestaurantApiService::class.java)
}

@Composable
fun RestaurantApp(
    navController: NavHostController = rememberNavController()
){
    val retrofit = createRetrofit()
    val apiService = createApiService(retrofit)


    Surface {
        NavHost(
            navController = navController,
            startDestination = RestaurantScreen.Login.name
        ){
            composable(route = RestaurantScreen.Login.name){
                LogInScreen(
                    onGoToRegister = {
                        navController.navigate(RestaurantScreen.Register.name)
                    }
                )
            }

            composable(route = RestaurantScreen.Register.name){
                RegisterScreen(
                    onGoToLogin = {
                        navController.navigate(RestaurantScreen.Login.name)
                    },
                    onRegister = {

                        fun lam (params:RegisterParams) {
                            val viewModel = RegisterViewModel(RestaurantRepository(apiService))
                            viewModel.registerUser(params)
                        }

                    }
                )
            }
        }
    }
}

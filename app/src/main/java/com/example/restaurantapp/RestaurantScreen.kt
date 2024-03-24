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
import com.example.restaurantapp.network.LoginParams
import com.example.restaurantapp.network.RegisterParams
import com.example.restaurantapp.network.RestaurantApiService
import com.example.restaurantapp.ui.screens.HomeScreen
import com.example.restaurantapp.ui.screens.LogInScreen
import com.example.restaurantapp.ui.screens.LoginViewModel
import com.example.restaurantapp.ui.screens.RegisterScreen
import com.example.restaurantapp.ui.screens.RegisterViewModel
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

enum class RestaurantScreen {
    Login,
    Register,
    Home
}

//private const val BASE_URL = "https://httpbin.org/"
private const val BASE_URL = "https://restaurantapi20240224142603.azurewebsites.net/"




fun createRetrofit(): Retrofit{

    var interceptor: HttpLoggingInterceptor = HttpLoggingInterceptor();
    interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
    var client: OkHttpClient = OkHttpClient.Builder().addInterceptor(interceptor).build();

    return Retrofit
        .Builder()
        .addConverterFactory(GsonConverterFactory.create())
        .baseUrl(BASE_URL)
        .client(client)
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
                    },
                    onGoToHome = {
                        navController.navigate((RestaurantScreen.Home.name))
                    },
                    onLogin = {
                        params: LoginParams ->
                            val viewModel = LoginViewModel(RestaurantRepository(apiService), navController)
                            viewModel.loginUser(params)
                    }
                )
            }

            composable(route = RestaurantScreen.Register.name){
                RegisterScreen(
                    onGoToLogin = {
                        navController.navigate(RestaurantScreen.Login.name)
                    },
                    onRegister = {
                        params:RegisterParams ->
                            val viewModel = RegisterViewModel(RestaurantRepository(apiService), navController)
                            viewModel.registerUser(params)
                    }
                )
            }

            composable(route = RestaurantScreen.Home.name){
                HomeScreen()
            }
        }
    }
}

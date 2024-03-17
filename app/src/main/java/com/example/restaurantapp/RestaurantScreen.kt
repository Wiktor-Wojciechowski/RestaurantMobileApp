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
import com.example.restaurantapp.network.RegisterParams
import com.example.restaurantapp.ui.screens.LogInScreen
import com.example.restaurantapp.ui.screens.RegisterScreen
import com.example.restaurantapp.ui.screens.RegisterViewModel

enum class RestaurantScreen {
    Login,
    Register
}

@Composable
fun RestaurantApp(
    navController: NavHostController = rememberNavController()
){
    val viewModel: RegisterViewModel = viewModel()
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
                        params:RegisterParams -> viewModel.register(params)
                    }
                )
            }
        }
    }
}

package com.example.restaurantapp

import androidx.compose.foundation.layout.Box
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.restaurantapp.ui.screens.LogInScreen
import com.example.restaurantapp.ui.screens.RegisterScreen

enum class RestaurantScreen {
    Login,
    Register
}

@Composable
fun RestaurantApp(
    navController: NavHostController = rememberNavController()
){
    Box {
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
                    }
                )
            }
        }
    }
}

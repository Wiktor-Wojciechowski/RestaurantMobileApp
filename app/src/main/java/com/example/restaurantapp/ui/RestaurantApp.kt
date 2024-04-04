package com.example.restaurantapp.ui

import android.util.Log
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.restaurantapp.data.RestaurantRepository
import com.example.restaurantapp.model.LoginParams
import com.example.restaurantapp.model.RegisterParams
import com.example.restaurantapp.ui.screens.HomeScreen
import com.example.restaurantapp.ui.screens.LogInScreen
import com.example.restaurantapp.ui.screens.LoginViewModel
import com.example.restaurantapp.ui.screens.OrderScreen
import com.example.restaurantapp.ui.screens.OrderState
import com.example.restaurantapp.ui.screens.OrderViewModel
import com.example.restaurantapp.ui.screens.RegisterScreen
import com.example.restaurantapp.ui.screens.RegisterViewModel

enum class RestaurantScreen {
    Login,
    Register,
    Home,
    Order
}



@Composable
fun RestaurantApp(
    navController: NavHostController = rememberNavController()
){
    Surface {
        NavHost(
            navController = navController,
            startDestination = RestaurantScreen.Login.name
        ){
            composable(route = RestaurantScreen.Login.name){
                val viewModel : LoginViewModel = viewModel(factory = LoginViewModel.Factory)
                LogInScreen(
                    loginState = viewModel.loginState,
                    onGoToRegister = {
                        navController.navigate(RestaurantScreen.Register.name)
                    },
                    onGoToHome = {
                        navController.navigate((RestaurantScreen.Home.name))
                    },
                    onLogin = {
                        params: LoginParams ->
                            viewModel.loginUser(params)

                    }
                )
            }

            composable(route = RestaurantScreen.Register.name){
                val viewModel : RegisterViewModel = viewModel(factory = RegisterViewModel.Factory)
                RegisterScreen(
                    onGoToLogin = {
                        navController.navigate(RestaurantScreen.Login.name)
                    },
                    onRegister = {
                        params: RegisterParams ->
                            viewModel.registerUser(params)
                    }
                )
            }

            composable(route = RestaurantScreen.Home.name){
                HomeScreen(
                    onGoToOrder = {
                        navController.navigate(RestaurantScreen.Order.name)
                    }
                )
            }
            
            composable(route = RestaurantScreen.Order.name){
                val viewmodel : OrderViewModel = viewModel(factory = OrderViewModel.Factory)
                OrderScreen(viewmodel.orderState)
            }
        }
    }
}

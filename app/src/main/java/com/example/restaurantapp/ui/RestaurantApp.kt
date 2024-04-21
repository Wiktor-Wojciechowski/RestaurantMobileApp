package com.example.restaurantapp.ui

import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import androidx.navigation.compose.rememberNavController
import com.example.restaurantapp.model.LoginParams
import com.example.restaurantapp.model.RegisterParams
import com.example.restaurantapp.ui.screens.FinalizeOrderScreen
import com.example.restaurantapp.ui.screens.FoodChoiceScreen
import com.example.restaurantapp.ui.screens.FoodChoiceViewModel
import com.example.restaurantapp.ui.screens.HomeScreen
import com.example.restaurantapp.ui.screens.LogInScreen
import com.example.restaurantapp.ui.screens.LoginViewModel
import com.example.restaurantapp.ui.screens.OrderViewModel
import com.example.restaurantapp.ui.screens.RegisterScreen
import com.example.restaurantapp.ui.screens.RegisterViewModel
import com.example.restaurantapp.ui.screens.TableChoiceScreen
import com.example.restaurantapp.ui.screens.TableChoiceViewModel

enum class RestaurantScreen {
    Login,
    Register,
    Home,
    Order,
    FoodChoice,
    TableChoice,
    FinalizeOrder
}

@Composable
fun RestaurantApp(
    navController: NavHostController = rememberNavController()
){
    val orderViewModel : OrderViewModel = viewModel(factory = OrderViewModel.Factory)
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

            navigation(
                startDestination = RestaurantScreen.FoodChoice.name,
                route = RestaurantScreen.Order.name
            ){

                composable(route = RestaurantScreen.FoodChoice.name){
                    val viewmodel : FoodChoiceViewModel = viewModel(factory = FoodChoiceViewModel.Factory)
                    FoodChoiceScreen(
                        foodState = viewmodel.foodState,
                        onGoToTableChoice = {
                            navController.navigate(RestaurantScreen.TableChoice.name)
                        },
                        onReturn = {
                            navController.navigate(RestaurantScreen.Home.name)
                        },
                        viewModel = viewmodel,
                        orderViewModel = orderViewModel
                    )
                }

                composable(route = RestaurantScreen.TableChoice.name){
                    val viewmodel: TableChoiceViewModel = viewModel(factory = TableChoiceViewModel.Factory)
                    TableChoiceScreen(
                        tablesState = viewmodel.tablesState,
                        infrastructureState = viewmodel.infrastructureState,
                        viewModel = viewmodel,
                        onTableChosen = {
                            navController.navigate(RestaurantScreen.FinalizeOrder.name)
                        },
                        orderViewModel = orderViewModel
                    )
                }

                composable(route = RestaurantScreen.FinalizeOrder.name){
                    FinalizeOrderScreen(orderState = orderViewModel.orderState, orderViewModel = orderViewModel)
                }
            }
        }
    }
}

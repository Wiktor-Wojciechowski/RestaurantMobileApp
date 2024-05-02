package com.example.restaurantapp.ui.screens

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.restaurantapp.RestaurantApplication
import com.example.restaurantapp.data.AuthContext
import com.example.restaurantapp.data.RestaurantRepository
import com.example.restaurantapp.model.ReceivedReservation
import kotlinx.coroutines.launch

interface UserReservationsState {
    object Error: UserReservationsState
    object Loading: UserReservationsState
    data class receivedReservations(val reservations: List<ReceivedReservation>): UserReservationsState
}

class UserReservationsViewModel(val repository: RestaurantRepository): ViewModel() {
    var userReservationsState: UserReservationsState by mutableStateOf(UserReservationsState.Loading)
        private set

    init {
        getUserReservations()
    }

    fun getUserReservations(){
        viewModelScope.launch {
            userReservationsState = UserReservationsState.Loading
            try {
                userReservationsState = UserReservationsState.receivedReservations(repository.getUserReservations("Bearer "+AuthContext.getUser().token, AuthContext.getUser().id))

            }catch (e:Exception){
                userReservationsState = UserReservationsState.Error
                Log.d("getuserreservationsException", e.toString())
            }
        }
    }
    companion object {
        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val application = (this[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY] as RestaurantApplication)
                val restaurantRepository = application.container.restaurantRepository
                UserReservationsViewModel(repository = restaurantRepository)
            }
        }
    }
}
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
import com.example.restaurantapp.model.SentReservation
import kotlinx.coroutines.launch
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

interface ReservationState {
    object Loading : ReservationState
    object Success : ReservationState
    object Init : ReservationState
    object Error : ReservationState
}

class MakeReservationViewModel(val repository: RestaurantRepository): ViewModel() {
    var tablesState: TablesState by mutableStateOf(TablesState.Loading)
        private set
    var infrastructureState: InfrastructureState by mutableStateOf(InfrastructureState.Loading)
        private set
    var reservationState: ReservationState by mutableStateOf(ReservationState.Init)
    var chosenTableId: Int? = null

    init {
        getInfrastrucutre()
        getTables()
    }

    fun getTables(){
        viewModelScope.launch {
            Log.d("table_init", "init")
            try{
                tablesState = TablesState.receivedTables(repository.getTables())
            }catch (e:Exception){
                tablesState = TablesState.Error
                Log.d("TableException", e.toString())
            }
        }
    }
    fun getInfrastrucutre(){
        viewModelScope.launch {
            try {
                infrastructureState = InfrastructureState.receivedInfrastructure(repository.getInfrastructure())
            }catch (e:Exception){

            }
        }
    }

    fun makeReservation(reservation: SentReservation) {
        viewModelScope.launch {
            try {
                var call = repository.makeReservation("Bearer "+AuthContext.getUser().token, reservation)
                call.enqueue(object: Callback<ResponseBody> {
                    override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                        Log.d("reservationFailure", t.message.toString())

                    }
                    override fun onResponse(
                        call: Call<ResponseBody>,
                        response: Response<ResponseBody>
                    ) {
                        if (response.isSuccessful){
                            Log.d("reservationSuccess", response.message())
                        }
                    }

                })
                reservationState = ReservationState.Success
            }catch (e:Exception){
                reservationState = ReservationState.Error
            }
        }
    }

    companion object {
        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val application = (this[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY] as RestaurantApplication)
                val restaurantRepository = application.container.restaurantRepository
                MakeReservationViewModel(repository = restaurantRepository)
            }
        }
    }
}
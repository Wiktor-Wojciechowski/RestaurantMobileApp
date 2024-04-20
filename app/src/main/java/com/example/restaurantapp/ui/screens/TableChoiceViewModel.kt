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
import com.example.restaurantapp.data.RestaurantRepository
import com.example.restaurantapp.model.Infrastructure
import com.example.restaurantapp.model.Table
import kotlinx.coroutines.launch

interface TablesState {
    data class receivedTables(val tables: List<Table>): TablesState
    object Loading : TablesState
    object Error : TablesState
}
interface InfrastructureState {
    data class receivedInfrastructure(val infrastructure: Infrastructure): InfrastructureState
    object Loading : InfrastructureState
    object Error : InfrastructureState
}

class TableChoiceViewModel(val repository: RestaurantRepository): ViewModel() {
    var tablesState: TablesState by mutableStateOf(TablesState.Loading)
        private set
    var infrastructureState: InfrastructureState by mutableStateOf(InfrastructureState.Loading)
        private set
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
    companion object {
        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val application = (this[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY] as RestaurantApplication)
                val restaurantRepository = application.container.restaurantRepository
                TableChoiceViewModel(repository = restaurantRepository)
            }
        }
    }
}
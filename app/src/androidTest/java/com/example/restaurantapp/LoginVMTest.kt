package com.example.restaurantapp

import android.util.Log
import com.example.restaurantapp.data.RestaurantRepository
import com.example.restaurantapp.model.LoginParams
import com.example.restaurantapp.ui.screens.LoginState
import com.example.restaurantapp.ui.screens.LoginViewModel
import io.mockk.coEvery
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Test
import org.junit.Assert.assertEquals
import org.junit.Before

class LoginVMTest {
    @Before
    fun setUp() {
        Dispatchers.setMain(Dispatchers.Unconfined)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `logintest`() {

        /*
        val repository = mockk<RestaurantRepository>()
        val viewmodel = LoginViewModel(repository)


        var params = LoginParams("login", "password")

        var expectedResult = LoginState.Success

        coEvery { repository.loginUser(params) }

        runBlocking { viewmodel.loginUser(params) }

        assertEquals(expectedResult, viewmodel.loginState)

         */

    }
}
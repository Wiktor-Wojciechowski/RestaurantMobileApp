package com.example.restaurantapp.ui.screens

import com.example.restaurantapp.data.RestaurantRepository
import com.example.restaurantapp.model.LoginParams
import com.example.restaurantapp.ui.screens.LoginState
import com.example.restaurantapp.ui.screens.LoginViewModel
import io.mockk.coVerify
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runBlockingTest
import kotlinx.coroutines.test.setMain
import okhttp3.ResponseBody
import okhttp3.ResponseBody.Companion.toResponseBody
import org.junit.After
import org.junit.Test
import org.junit.Assert.assertEquals
import org.junit.Before
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class LoginViewModelTest {

    private lateinit var loginViewModel: LoginViewModel
    private lateinit var restaurantRepository: RestaurantRepository
    private val testDispatcher = TestCoroutineDispatcher()

    @Before
    fun setup() {
        Dispatchers.setMain(testDispatcher)
        restaurantRepository = mockk()
        loginViewModel = LoginViewModel(restaurantRepository)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
        testDispatcher.cleanupTestCoroutines()
    }

    @Test
    fun testSuccessfulLogin() = runBlockingTest {
        val responseBodyString = "{\"token\": \"testToken\", \"userId\": \"123\"}"
        val responseBody = responseBodyString.toResponseBody(null)
        val mockCall = mockk<Call<ResponseBody>>()
        every { restaurantRepository.loginUser(any()) } returns mockCall
        every { mockCall.enqueue(any()) } answers {
            val callback = arg<Callback<ResponseBody>>(0)
            callback.onResponse(mockCall, Response.success(responseBody))
        }

        loginViewModel.loginUser(LoginParams("username", "password"))

        assertEquals(LoginState.Success, loginViewModel.loginState)
        coVerify { restaurantRepository.loginUser(any()) }
    }
}
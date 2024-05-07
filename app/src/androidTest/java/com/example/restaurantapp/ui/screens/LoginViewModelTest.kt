import com.example.restaurantapp.data.RestaurantRepository
import com.example.restaurantapp.model.LoginParams
import com.example.restaurantapp.ui.screens.LoginState
import com.example.restaurantapp.ui.screens.LoginViewModel
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runBlockingTest
import kotlinx.coroutines.test.setMain
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.ResponseBody
import okhttp3.ResponseBody.Companion.toResponseBody
import org.junit.After
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class LoginViewModelTest {

    @Test
    fun loginUser_CallsRepository() = runBlockingTest {
        val repository = mockk<RestaurantRepository>()
        val viewModel = LoginViewModel(repository)
        val params = LoginParams("username", "password")
        val call = mockk<Call<ResponseBody>>()
        val responseBody = "response".toResponseBody("application/json".toMediaType())
        val response = Response.success(responseBody)
        coEvery { repository.loginUser(params) } returns call
        coEvery { call.enqueue(any()) } answers {
            val callback = firstArg<Callback<ResponseBody>>()
            callback.onResponse(call, response)
        }

        viewModel.loginUser(params)

        coVerify { repository.loginUser(params) }
        coVerify { call.enqueue(any<Callback<ResponseBody>>()) }
    }
    private val testDispatcher = TestCoroutineDispatcher()
    @Before
    fun setup() {
        Dispatchers.setMain(testDispatcher)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
        testDispatcher.cleanupTestCoroutines()
    }
    @Test
    fun loginUser_UpdatesLoginState() = runBlockingTest {
        val repository = mockk<RestaurantRepository>()
        val viewModel = LoginViewModel(repository)
        val params = LoginParams("username", "password")
        val call = mockk<Call<ResponseBody>>()
        val responseBody = "{\"token\":\"token\",\"userId\":\"userId\"}".toResponseBody("application/json".toMediaType())
        val response = Response.success(responseBody)
        coEvery { repository.loginUser(params) } returns call
        coEvery { call.enqueue(any()) } answers {
            val callback = firstArg<Callback<ResponseBody>>()
            callback.onResponse(call, response)
        }

        viewModel.loginUser(params)

        Assert.assertTrue(viewModel.loginState is LoginState.Success)
    }
}

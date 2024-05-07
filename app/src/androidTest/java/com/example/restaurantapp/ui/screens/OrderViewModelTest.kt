import android.util.Log
import com.example.restaurantapp.data.AuthContext
import com.example.restaurantapp.data.RestaurantRepository
import com.example.restaurantapp.model.Order
import com.example.restaurantapp.model.Table
import com.example.restaurantapp.model.User
import com.example.restaurantapp.ui.screens.OrderState
import com.example.restaurantapp.ui.screens.OrderViewModel
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

class OrderViewModelTest {

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
    fun sendOrder_CallsRepository() = runBlockingTest {

        val repository = mockk<RestaurantRepository>()
        val viewModel = OrderViewModel(repository)
        AuthContext.setUser(User("abc", "def"))
        val order = Order( 1, listOf(1, 2, 3), "userId")
        val call = mockk<Call<ResponseBody>>()
        val responseBody = "response".toResponseBody("application/json".toMediaType())
        val response = Response.success(responseBody)
        coEvery { repository.sendOrder(any(), any()) } returns call
        coEvery { call.enqueue(any()) } answers {
            val callback = firstArg<Callback<ResponseBody>>()
            callback.onResponse(call, response)
        }

        viewModel.setCart(order.dishModelsId)
        viewModel.setTable(Table(order.tableModelId, true,4, 4, 4))
        viewModel.sendOrder()

        coVerify { repository.sendOrder(any(), any()) }
        coVerify { call.enqueue(any<Callback<ResponseBody>>()) }
    }

    @Test
    fun sendOrder_UpdatesOrderState() = runBlockingTest {
        val repository = mockk<RestaurantRepository>()
        val viewModel = OrderViewModel(repository)
        AuthContext.setUser(User("abc", "def"))
        val order = Order(1,  listOf(1, 2, 3), "userId")
        val call = mockk<Call<ResponseBody>>()
        val responseBody = "{\"message\":\"Order placed successfully\"}".toResponseBody("application/json".toMediaType())
        val response = Response.success(responseBody)
        coEvery { repository.sendOrder(any(), any()) } returns call
        coEvery { call.enqueue(any()) } answers {
            val callback = firstArg<Callback<ResponseBody>>()
            callback.onResponse(call, response)
        }

        viewModel.setCart(order.dishModelsId)
        viewModel.setTable(Table(order.tableModelId, true,  1, 4, 4))
        viewModel.sendOrder()

        Assert.assertTrue(viewModel.orderState is OrderState.Success)
    }
}

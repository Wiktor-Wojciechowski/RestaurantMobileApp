import com.example.restaurantapp.data.AuthContext
import com.example.restaurantapp.data.RestaurantRepository
import com.example.restaurantapp.model.SentReservation
import com.example.restaurantapp.model.User
import com.example.restaurantapp.ui.screens.MakeReservationViewModel
import com.example.restaurantapp.ui.screens.ReservationState
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

class MakeReservationViewModelTest {

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
    fun makeReservation_CallsRepository() = runBlockingTest {
        val repository = mockk<RestaurantRepository>()
        val viewModel = MakeReservationViewModel(repository)
        AuthContext.setUser(User("abc", "def"))
        val reservation = SentReservation("1", "2", "abc", 4)
        val call = mockk<Call<ResponseBody>>()
        val responseBody = "response".toResponseBody("application/json".toMediaType())
        val response = Response.success(responseBody)
        coEvery { repository.makeReservation(any(), any()) } returns call
        coEvery { call.enqueue(any()) } answers {
            val callback = firstArg<Callback<ResponseBody>>()
            callback.onResponse(call, response)
        }

        viewModel.makeReservation(reservation)

        coVerify { repository.makeReservation(any(), any()) }
        coVerify { call.enqueue(any<Callback<ResponseBody>>()) }
    }

    @Test
    fun makeReservation_UpdatesReservationState() = runBlockingTest {
        val repository = mockk<RestaurantRepository>()
        val viewModel = MakeReservationViewModel(repository)
        AuthContext.setUser(User("abc", "def"))
        val reservation = SentReservation("1", "2", "abc", 4)
        val call = mockk<Call<ResponseBody>>()
        val responseBody = "{\"message\":\"Reservation made successfully\"}".toResponseBody("application/json".toMediaType())
        val response = Response.success(responseBody)
        coEvery { repository.makeReservation(any(), any()) } returns call
        coEvery { call.enqueue(any()) } answers {
            val callback = firstArg<Callback<ResponseBody>>()
            callback.onResponse(call, response)
        }

        viewModel.makeReservation(reservation)

        Assert.assertTrue(viewModel.reservationState is ReservationState.Success)
    }
}

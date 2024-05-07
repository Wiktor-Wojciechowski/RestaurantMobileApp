import com.example.restaurantapp.data.RestaurantRepository
import com.example.restaurantapp.model.RegisterParams
import com.example.restaurantapp.ui.screens.RegisterViewModel
import io.mockk.Runs
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.just
import io.mockk.mockk
import kotlinx.coroutines.test.runBlockingTest
import okhttp3.ResponseBody
import org.junit.Test
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class RegisterViewModelTest {

    @Test
    fun testSuccessfulRegister() = runBlockingTest {
        val repository = mockk<RestaurantRepository>()
        val viewModel = RegisterViewModel(repository)
        val params = RegisterParams("username", "email", "password", "20")
        val call = mockk<Call<ResponseBody>>()
        val response = mockk<Response<ResponseBody>>()
        coEvery { repository.registerUser(params) } returns call
        coEvery { call.enqueue(any()) } answers {
            val callback = firstArg<Callback<ResponseBody>>()
            callback.onResponse(call, response)
        }

        viewModel.registerUser(params)

        coVerify { repository.registerUser(params) }
        coVerify { call.enqueue(any<Callback<ResponseBody>>()) }
    }
}

import com.example.restaurantapp.data.AuthContext
import com.example.restaurantapp.data.RestaurantRepository
import com.example.restaurantapp.model.ReceivedReservation
import com.example.restaurantapp.model.User
import com.example.restaurantapp.ui.screens.UserReservationsState
import com.example.restaurantapp.ui.screens.UserReservationsViewModel
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runBlockingTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert
import org.junit.Before
import org.junit.Test

class UserReservationsViewModelTest {

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
    fun getUserReservations_CallsRepository() = runBlockingTest {
        val repository = mockk<RestaurantRepository>()
        val viewModel = UserReservationsViewModel(repository)
        AuthContext.setUser(User("abc", "def"))
        val reservations = listOf(
            ReceivedReservation(
                1,
                "dateF",
                "dateTo",
                "identity",
                3
            )
        )
        coEvery { repository.getUserReservations(any(), any()) } returns reservations

        viewModel.getUserReservations()

        coVerify { repository.getUserReservations(any(), any()) }
    }

    @Test
    fun getUserReservations_UpdatesUserReservationsState() = runBlockingTest {
        val repository = mockk<RestaurantRepository>()
        val viewModel = UserReservationsViewModel(repository)
        AuthContext.setUser(User("abc", "def"))
        val reservations = listOf(
            ReceivedReservation(
                1,
                "dateF",
                "dateTo",
                "identity",
                3
            )
        )
        coEvery { repository.getUserReservations(any(), any()) } returns reservations

        viewModel.getUserReservations()

        val expectedState = UserReservationsState.receivedReservations(reservations)
        Assert.assertEquals(expectedState, viewModel.userReservationsState)
    }

    @Test
    fun deleteReservation_CallsRepository() = runBlockingTest {
        val repository = mockk<RestaurantRepository>()
        val viewModel = UserReservationsViewModel(repository)
        AuthContext.setUser(User("abc", "def"))
        val reservationId = 1
        coEvery { repository.deleteReservation(any(), any()) } returns mockk(relaxed = true)

        viewModel.deleteReservation(reservationId)

        coVerify { repository.deleteReservation(reservationId, any()) }
    }
}

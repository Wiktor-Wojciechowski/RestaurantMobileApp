import com.example.restaurantapp.data.AuthContext
import com.example.restaurantapp.data.RestaurantRepository
import com.example.restaurantapp.model.ReceivedDish
import com.example.restaurantapp.model.ReceivedOrder
import com.example.restaurantapp.model.ReceivedTable
import com.example.restaurantapp.model.User
import com.example.restaurantapp.ui.screens.UserOrdersState
import com.example.restaurantapp.ui.screens.UserOrdersViewModel
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

class UserOrdersViewModelTest {

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
    fun getOrders_CallsRepository() = runBlockingTest {
        val repository = mockk<RestaurantRepository>()
        val viewModel = UserOrdersViewModel(repository)
        AuthContext.setUser(User("abc", "def"))
        val orders = listOf(
            ReceivedOrder(
                1,
                2,
                3,
                4,
                ReceivedTable(
                    1,
                    true,
                    4,
                    4,
                    4,
                    Any(),
                    Any()
                ),
                listOf(
                    ReceivedDish(
                        1,
                        "name",
                        "desc",
                        5,
                        true,
                        intArrayOf(1,2,3)
                    )
                ),
                "userId",
                "Aa"
            )
        )
        coEvery { repository.getOrders(any(), any()) } returns orders

        viewModel.getOrders()

        coVerify { repository.getOrders(any(), any()) }
    }

    @Test
    fun getOrders_UpdatesUserOrdersState() = runBlockingTest {
        val repository = mockk<RestaurantRepository>()
        val viewModel = UserOrdersViewModel(repository)
        AuthContext.setUser(User("abc", "def"))
        val orders = listOf(
            ReceivedOrder(
                1,
                2,
                3,
                4,
                ReceivedTable(
                    1,
                    true,
                    4,
                    4,
                    4,
                    intArrayOf(1,2,3),
                    intArrayOf(1,2,3)
                ),
                listOf(
                    ReceivedDish(
                        1,
                        "name",
                        "desc",
                        5,
                        true,
                        Any())
                ),
                "userId",
                "Aa"
            )
        )
        coEvery { repository.getOrders(any(), any()) } returns orders

        viewModel.getOrders()

        val expectedState = UserOrdersState.receivedOrders(orders)
        Assert.assertEquals(expectedState, viewModel.userOrdersState)
    }

    @Test
    fun setOrderReadyToPay_CallsRepository() = runBlockingTest {
        val repository = mockk<RestaurantRepository>()
        val viewModel = UserOrdersViewModel(repository)
        AuthContext.setUser(User("abc", "def"))
        val orderId = 1
        coEvery { repository.setOrderReadyToPay(any(), any()) } returns mockk(relaxed = true)

        viewModel.setOrderReadyToPay(orderId)

        coVerify { repository.setOrderReadyToPay(orderId, any()) }
    }
}

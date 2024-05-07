import com.example.restaurantapp.data.RestaurantRepository
import com.example.restaurantapp.model.Dish
import com.example.restaurantapp.ui.screens.FoodChoiceViewModel
import com.example.restaurantapp.ui.screens.FoodState
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.test.runBlockingTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.TestCoroutineDispatcher

class FoodChoiceViewModelTest {

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
    fun getDishes_CallsRepository() = runBlockingTest {
        val repository = mockk<RestaurantRepository>()
        val viewModel = FoodChoiceViewModel(repository)
        val dishes = listOf<Dish>()
        coEvery { repository.getDishes() } returns dishes

        viewModel.getDishes()

        coVerify { repository.getDishes() }
    }

    @Test
    fun getDishes_UpdatesFoodState() = runBlockingTest {
        val repository = mockk<RestaurantRepository>()
        val viewModel = FoodChoiceViewModel(repository)
        val dishes = listOf<Dish>()
        coEvery { repository.getDishes() } returns dishes

        viewModel.getDishes()

        Assert.assertTrue(viewModel.foodState is FoodState.receivedDishes)
    }

    @Test
    fun addDishToCart_AddsDish() = runBlockingTest {
        val repository = mockk<RestaurantRepository>()
        val viewModel = FoodChoiceViewModel(repository)
        val dishId = 1

        viewModel.addDishToCart(dishId)

        Assert.assertTrue(viewModel.cart.contains(dishId))
    }

    @Test
    fun removeDishFromCart_RemovesDish() = runBlockingTest {
        val repository = mockk<RestaurantRepository>()
        val viewModel = FoodChoiceViewModel(repository)
        val dishId = 1
        viewModel.addDishToCart(dishId)

        viewModel.removeDishFromCart(dishId)

        Assert.assertFalse(viewModel.cart.contains(dishId))
    }
}

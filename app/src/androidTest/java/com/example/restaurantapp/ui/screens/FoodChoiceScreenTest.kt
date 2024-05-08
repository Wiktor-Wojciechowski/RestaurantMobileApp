import androidx.compose.ui.test.*
import androidx.compose.ui.test.junit4.createComposeRule
import com.example.restaurantapp.data.AuthContext
import com.example.restaurantapp.data.RestaurantRepository
import com.example.restaurantapp.model.User
import com.example.restaurantapp.ui.screens.FoodChoiceScreen
import com.example.restaurantapp.ui.screens.FoodChoiceViewModel
import com.example.restaurantapp.ui.screens.FoodState
import com.example.restaurantapp.ui.screens.OrderViewModel
import io.mockk.mockk
import org.junit.Rule
import org.junit.Test

class FoodChoiceScreenTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun foodChoiceScreen_Displayed_Correctly() {
        var repository = mockk<RestaurantRepository>()
        AuthContext.setUser(User("abc", "def"))
        composeTestRule.setContent {
            FoodChoiceScreen(
                foodState = FoodState.Loading,
                viewModel = FoodChoiceViewModel(repository),
                orderViewModel = OrderViewModel(repository)
            )
        }

        composeTestRule.onNodeWithText("Choose a table").assertIsDisplayed()
        composeTestRule.onNodeWithText("Return").assertIsDisplayed()
    }

    @Test
    fun foodChoiceScreen_FoodStateError_ErrorDisplayed() {
        var repository = mockk<RestaurantRepository>()
        AuthContext.setUser(User("abc", "def"))
        composeTestRule.setContent {
            FoodChoiceScreen(
                foodState = FoodState.Error,
                viewModel = FoodChoiceViewModel(repository),
                orderViewModel = OrderViewModel(repository)
            )
        }

        composeTestRule.onNodeWithText("Error").assertIsDisplayed()
    }

    @Test
    fun foodChoiceScreen_GoToTableChoiceButtonClicked_InitCartEmpty() {
        var repository = mockk<RestaurantRepository>()
        AuthContext.setUser(User("abc", "def"))
        var goToTableChoiceCalled = false
        composeTestRule.setContent {
            FoodChoiceScreen(
                onGoToTableChoice = { goToTableChoiceCalled = true },
                foodState = FoodState.Loading,
                viewModel = FoodChoiceViewModel(repository),
                orderViewModel = OrderViewModel(repository)
            )
        }

        composeTestRule.onNodeWithText("Choose a table").performClick()

        assert(!goToTableChoiceCalled)
    }
}

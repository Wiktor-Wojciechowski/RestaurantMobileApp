import androidx.compose.ui.test.*
import androidx.compose.ui.test.junit4.createComposeRule
import com.example.restaurantapp.data.AuthContext
import com.example.restaurantapp.data.RestaurantRepository
import com.example.restaurantapp.model.User
import com.example.restaurantapp.ui.screens.FinalizeOrderScreen
import com.example.restaurantapp.ui.screens.OrderState
import com.example.restaurantapp.ui.screens.OrderViewModel
import io.mockk.mockk
import org.junit.Rule
import org.junit.Test

class FinalizeOrderScreenTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun finalizeOrderScreen_Displayed_Correctly() {
        var repository = mockk<RestaurantRepository>()
        AuthContext.setUser(User("abc", "def"))
        composeTestRule.setContent {
            FinalizeOrderScreen(
                orderViewModel = OrderViewModel(repository),
                orderState = OrderState.Loading
            )
        }

        composeTestRule.onNodeWithText("Finalize Order").assertIsDisplayed()
        composeTestRule.onNodeWithText("Return Home").assertIsDisplayed()
    }

    @Test
    fun finalizeOrderScreen_OrderStateError_ErrorDisplayed() {
        var repository = mockk<RestaurantRepository>()
        AuthContext.setUser(User("abc", "def"))
        composeTestRule.setContent {
            FinalizeOrderScreen(
                orderViewModel = OrderViewModel(repository),
                orderState = OrderState.Error
            )
        }

        composeTestRule.onNodeWithText("Something went wrong").assertIsDisplayed()
    }

    @Test
    fun finalizeOrderScreen_FinalizeOrderButtonClicked() {
        var repository = mockk<RestaurantRepository>()
        AuthContext.setUser(User("abc", "def"))
        var sendOrderCalled = false
        composeTestRule.setContent {
            FinalizeOrderScreen(
                orderViewModel = OrderViewModel(repository).apply {
                    sendOrder().apply { sendOrderCalled = true }
                },
                orderState = OrderState.Loading
            )
        }

        composeTestRule.onNodeWithText("Finalize Order").performClick()

        assert(sendOrderCalled)
    }
}

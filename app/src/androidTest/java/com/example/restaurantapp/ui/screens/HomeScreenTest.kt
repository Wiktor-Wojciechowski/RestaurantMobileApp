import androidx.compose.ui.test.*
import androidx.compose.ui.test.junit4.createComposeRule
import com.example.restaurantapp.ui.screens.HomeScreen
import org.junit.Rule
import org.junit.Test

class HomeScreenTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun homeScreen_Displayed_Correctly() {
        composeTestRule.setContent {
            HomeScreen()
        }

        composeTestRule.onNodeWithText("Order Food").assertIsDisplayed()
        composeTestRule.onNodeWithText("My Orders").assertIsDisplayed()
        composeTestRule.onNodeWithText("Make a Reservation").assertIsDisplayed()
        composeTestRule.onNodeWithText("My Reservations").assertIsDisplayed()
    }

    @Test
    fun homeScreen_OrderFoodButtonClicked() {
        var goToOrderCalled = false
        composeTestRule.setContent {
            HomeScreen(onGoToOrder = { goToOrderCalled = true })
        }

        composeTestRule.onNodeWithText("Order Food").performClick()

        assert(goToOrderCalled)
    }

}

import androidx.compose.ui.test.*
import androidx.compose.ui.test.junit4.createComposeRule
import com.example.restaurantapp.model.ReceivedOrder
import com.example.restaurantapp.model.ReceivedTable
import com.example.restaurantapp.ui.screens.UserOrdersScreen
import com.example.restaurantapp.ui.screens.UserOrdersState
import org.junit.Rule
import org.junit.Test

class UserOrdersScreenTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun userOrdersScreen_Displayed_Correctly() {
        composeTestRule.setContent {
            UserOrdersScreen(userOrdersState = UserOrdersState.Loading)
        }

        composeTestRule.onNodeWithText("Loading").assertIsDisplayed()
    }

    @Test
    fun userOrdersScreen_UserOrdersStateError_ErrorDisplayed() {
        composeTestRule.setContent {
            UserOrdersScreen(userOrdersState = UserOrdersState.Error)
        }

        composeTestRule.onNodeWithText("Error").assertIsDisplayed()
    }

    @Test
    fun userOrdersScreen_ReadyToPayButtonClicked() {
        var setReadyToPayCalled = false
        composeTestRule.setContent {
            UserOrdersScreen(
                userOrdersState = UserOrdersState.receivedOrders(
                    listOf(
                        ReceivedOrder(
                            1,
                            1,
                            1,
                            1,
                            ReceivedTable(1,true,4,4,4,Any(),Any(),),
                            listOf(),
                            "id",
                            Any()
                        )
                    )
                ),
                onSetReadyToPay = { setReadyToPayCalled = true }
            )
        }

        composeTestRule.onNodeWithText("Ready to Pay").performClick()

        assert(setReadyToPayCalled)
    }
}

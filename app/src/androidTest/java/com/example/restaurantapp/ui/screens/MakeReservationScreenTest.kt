import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.ui.test.*
import androidx.compose.ui.test.junit4.createComposeRule
import com.example.restaurantapp.ui.screens.MakeReservationScreen
import com.example.restaurantapp.ui.screens.ReservationState
import com.example.restaurantapp.ui.screens.TablesState
import com.example.restaurantapp.ui.screens.InfrastructureState
import org.junit.Rule
import org.junit.Test

class MakeReservationScreenTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    @OptIn(ExperimentalMaterial3Api::class)
    @Test
    fun makeReservationScreen_Displayed_Correctly() {
        composeTestRule.setContent {
            MakeReservationScreen(
                reservationState = ReservationState.Init,
                tablesState = TablesState.Loading,
                infrastructureState = InfrastructureState.Loading
            )
        }

        composeTestRule.onNodeWithText("Choose Table").assertIsDisplayed()
        composeTestRule.onNodeWithText("Choose Date From").assertIsDisplayed()
        composeTestRule.onNodeWithText("Choose Time From").assertIsDisplayed()
        composeTestRule.onNodeWithText("Choose Date To").assertIsDisplayed()
        composeTestRule.onNodeWithText("Choose Time To").assertIsDisplayed()
        composeTestRule.onNodeWithText("Make a Reservation").assertIsDisplayed()
    }

    @OptIn(ExperimentalMaterial3Api::class)
    @Test
    fun makeReservationScreen_ReservationStateError_ErrorDisplayed() {
        composeTestRule.setContent {
            MakeReservationScreen(
                reservationState = ReservationState.Error,
                tablesState = TablesState.Loading,
                infrastructureState = InfrastructureState.Loading
            )
        }

        composeTestRule.onNodeWithText("Error").assertIsDisplayed()
    }
}

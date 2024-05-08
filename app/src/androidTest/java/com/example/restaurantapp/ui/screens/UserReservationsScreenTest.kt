import androidx.compose.ui.test.*
import androidx.compose.ui.test.junit4.createComposeRule
import com.example.restaurantapp.model.ReceivedReservation
import com.example.restaurantapp.ui.screens.UserReservationsScreen
import com.example.restaurantapp.ui.screens.UserReservationsState
import org.junit.Rule
import org.junit.Test

class UserReservationsScreenTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun userReservationsScreen_Displayed_Correctly() {
        composeTestRule.setContent {
            UserReservationsScreen(userReservationsState = UserReservationsState.Loading)
        }

        composeTestRule.onNodeWithText("Loading").assertIsDisplayed()
    }

    @Test
    fun userReservationsScreen_UserReservationsStateError_ErrorDisplayed() {
        composeTestRule.setContent {
            UserReservationsScreen(userReservationsState = UserReservationsState.Error)
        }

        composeTestRule.onNodeWithText("Error").assertIsDisplayed()
    }

    @Test
    fun userReservationsScreen_DeleteReservationButtonClicked() {
        var deleteReservationCalled = false
        composeTestRule.setContent {
            UserReservationsScreen(
                userReservationsState = UserReservationsState.receivedReservations(listOf(
                    ReceivedReservation(1,"21", "22", "def", 5)
                )),
                onDeleteReservation = { deleteReservationCalled = true }
            )
        }

        composeTestRule.onNodeWithText("Delete reservation").performClick()

        assert(deleteReservationCalled)
    }
}

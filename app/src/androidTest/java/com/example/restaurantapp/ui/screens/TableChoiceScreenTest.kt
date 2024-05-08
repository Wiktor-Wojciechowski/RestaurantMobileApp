import androidx.compose.ui.test.*
import androidx.compose.ui.test.junit4.createComposeRule
import com.example.restaurantapp.data.RestaurantRepository
import com.example.restaurantapp.model.Infrastructure
import com.example.restaurantapp.ui.screens.TableChoiceScreen
import com.example.restaurantapp.ui.screens.TablesState
import com.example.restaurantapp.ui.screens.InfrastructureState
import com.example.restaurantapp.ui.screens.OrderViewModel
import com.example.restaurantapp.ui.screens.TableChoiceViewModel
import io.mockk.mockk
import org.junit.Rule
import org.junit.Test

class TableChoiceScreenTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun tableChoiceScreen_Displayed_Correctly() {
        var repository = mockk<RestaurantRepository>()
        composeTestRule.setContent {
            TableChoiceScreen(
                tablesState = TablesState.Loading,
                infrastructureState = InfrastructureState.Loading,
                orderViewModel = OrderViewModel(repository),
                viewModel = TableChoiceViewModel(repository)
            )
        }

        composeTestRule.onNodeWithText("Loading").assertIsDisplayed()
    }

    @Test
    fun tableChoiceScreen_TablesStateError_ErrorDisplayed() {
        var repository = mockk<RestaurantRepository>()
        composeTestRule.setContent {
            TableChoiceScreen(
                tablesState = TablesState.Error,
                infrastructureState = InfrastructureState.receivedInfrastructure(Infrastructure(1,1)),
                orderViewModel = OrderViewModel(repository),
                viewModel = TableChoiceViewModel(repository)
            )
        }

        composeTestRule.onNodeWithText("Error").assertIsDisplayed()
    }

    @Test
    fun tableChoiceScreen_InfrastructureStateError_ErrorDisplayed() {
        var repository = mockk<RestaurantRepository>()
        composeTestRule.setContent {
            TableChoiceScreen(
                tablesState = TablesState.Loading,
                infrastructureState = InfrastructureState.Error,
                orderViewModel = OrderViewModel(repository),
                viewModel = TableChoiceViewModel(repository)
            )
        }

        composeTestRule.onNodeWithText("Error").assertIsDisplayed()
    }
}

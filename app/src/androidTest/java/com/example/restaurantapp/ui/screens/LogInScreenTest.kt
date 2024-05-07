import androidx.compose.ui.test.*
import androidx.compose.ui.test.junit4.createComposeRule
import com.example.restaurantapp.ui.screens.LogInScreen
import com.example.restaurantapp.ui.screens.LoginState
import org.junit.Rule
import org.junit.Test

class LogInScreenTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun logInScreen_Displayed_Correctly() {
        composeTestRule.setContent {
            LogInScreen()
        }

        composeTestRule.onNodeWithText("Username").assertIsDisplayed()
        composeTestRule.onNodeWithText("Password").assertIsDisplayed()
        composeTestRule.onNode(hasText("Log In") and hasClickAction()).assertIsDisplayed()
        composeTestRule.onNodeWithText("Go to Register").assertIsDisplayed()
    }

    @Test
    fun logInScreen_UsernameAndPasswordFilled_LoginButtonClicked() {
        var loginCalled = false
        composeTestRule.setContent {
            LogInScreen(onLogin = {
                loginCalled = true
            })
        }

        composeTestRule.onNodeWithText("Username").performTextInput("user")
        composeTestRule.onNodeWithText("Password").performTextInput("password")

        composeTestRule.onNode(hasText("Log In") and hasClickAction()).performClick()

        assert(loginCalled)
    }

    @Test
    fun logInScreen_LoginStateError_ErrorDisplayed() {
        composeTestRule.setContent {
            LogInScreen(loginState = LoginState.Error("Test error message"))
        }

        composeTestRule.onNodeWithText("Test error message").assertIsDisplayed()
    }
}

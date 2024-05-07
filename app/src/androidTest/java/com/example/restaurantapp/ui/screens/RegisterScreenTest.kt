import androidx.compose.ui.test.*
import androidx.compose.ui.test.junit4.createComposeRule
import com.example.restaurantapp.ui.screens.RegisterScreen
import com.example.restaurantapp.ui.screens.RegistrationState
import org.junit.Rule
import org.junit.Test

class RegisterScreenTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun registerScreen_Displayed_Correctly() {
        composeTestRule.setContent {
            RegisterScreen(registrationState = RegistrationState.Init)
        }

        composeTestRule.onNodeWithText("Username").assertIsDisplayed()
        composeTestRule.onNodeWithText("Email").assertIsDisplayed()
        composeTestRule.onNodeWithText("Password").assertIsDisplayed()
        composeTestRule.onNodeWithText("Age").assertIsDisplayed()
        composeTestRule.onNode(hasText("Register") and hasClickAction()).assertIsDisplayed()
        composeTestRule.onNodeWithText("Go to Log In").assertIsDisplayed()
    }

    @Test
    fun registerScreen_UsernameEmailPasswordAndAgeFilled_RegisterButtonClicked() {
        var registerCalled = false
        composeTestRule.setContent {
            RegisterScreen(
                onRegister = {
                    params ->
                        registerCalled = true
                },
                registrationState = RegistrationState.Init
            )
        }

        composeTestRule.onNodeWithText("Username").performTextInput("test")
        composeTestRule.onNodeWithText("Email").performTextInput("test@test.com")
        composeTestRule.onNodeWithText("Password").performTextInput("test")
        composeTestRule.onNodeWithText("Age").performTextInput("20")

        composeTestRule.onNode(hasText("Register") and hasClickAction()).performClick()

        assert(registerCalled)
    }

    @Test
    fun registerScreen_RegistrationStateError_ErrorDisplayed() {
        composeTestRule.setContent {
            RegisterScreen(registrationState = RegistrationState.Error)
        }

        composeTestRule.onNodeWithText("Error").assertIsDisplayed()
    }
}

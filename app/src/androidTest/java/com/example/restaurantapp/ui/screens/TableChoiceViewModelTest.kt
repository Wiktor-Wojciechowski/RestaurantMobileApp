import com.example.restaurantapp.data.RestaurantRepository
import com.example.restaurantapp.model.Infrastructure
import com.example.restaurantapp.model.Table
import com.example.restaurantapp.ui.screens.TableChoiceViewModel
import com.example.restaurantapp.ui.screens.TablesState
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runBlockingTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert
import org.junit.Before
import org.junit.Test

class TableChoiceViewModelTest {

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
    fun getTables_CallsRepository() = runBlockingTest {
        val repository = mockk<RestaurantRepository>()
        val viewModel = TableChoiceViewModel(repository)
        coEvery { repository.getTables() } returns listOf()

        viewModel.getTables()

        coVerify { repository.getTables() }
    }

    @Test fun getTables_UpdatesTablesState() = runBlockingTest {
        val repository = mockk<RestaurantRepository>()
        val viewModel = TableChoiceViewModel(repository)
        val tables = listOf(Table(1, true, 4, 5, 6))
        coEvery { repository.getTables() } returns tables

        viewModel.getTables()

        Assert.assertEquals(TablesState.receivedTables(tables), viewModel.tablesState)
    }

    @Test
    fun getInfrastructure_CallsRepository() = runBlockingTest {
        val repository = mockk<RestaurantRepository>()
        val viewModel = TableChoiceViewModel(repository)
        coEvery { repository.getInfrastructure() } returns Infrastructure(4,4)

        viewModel.getInfrastrucutre()

        coVerify { repository.getInfrastructure() }
    }
}

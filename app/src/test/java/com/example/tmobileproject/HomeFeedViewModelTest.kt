import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.tmobileproject.data.remote.model.Attributes
import com.example.tmobileproject.data.remote.model.Card
import com.example.tmobileproject.data.remote.model.CardX
import com.example.tmobileproject.data.remote.model.Description
import com.example.tmobileproject.data.remote.model.Font
import com.example.tmobileproject.data.remote.model.HomeFeedPage
import com.example.tmobileproject.data.remote.model.Image
import com.example.tmobileproject.data.remote.model.Size
import com.example.tmobileproject.data.remote.model.Title
import com.example.tmobileproject.data.repo.HomeFeedRepository
import com.example.tmobileproject.ui.HomeFeedViewModel
import com.example.tmobileproject.util.Resource
import io.mockk.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.setMain
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.StandardTestDispatcher

@ExperimentalCoroutinesApi
class HomeFeedViewModelTest {

    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()

    private lateinit var repository: HomeFeedRepository
    private lateinit var viewModel: HomeFeedViewModel

    private val testDispatcher = StandardTestDispatcher()

    @Before
    fun setup() {
        // Set the main dispatcher for testing
        Dispatchers.setMain(testDispatcher)


        // Mock the repository
        repository = mockk()

        // Initialize the ViewModel with the mocked repository
        viewModel = HomeFeedViewModel(repository)
    }

    @Test
    fun `fetchHomeFeed should update state on success`() = runBlocking {
        val card1 = Card(card = CardX(
            attributes = null,
            description = null,
            image = null,
            title = null,
            value = "one"
        ), cardType = "text")

        val card2 = Card(card = CardX(
            attributes = null,
            description = null,
            image = null,
            title = null,
            value = null
        ), cardType = "title_description")

        val card3 = Card(card = CardX(
            attributes = null,
            description = null,
            image = null,
            title = null,
            value = null
        ), cardType = "image_title_description")

        val homeFeedPage = HomeFeedPage(cards = listOf(card1, card2, card3))

        // Properly mock the repository response
        println("Setting up mock for getHomeFeed")
        coEvery { repository.getHomeFeed() } returns Resource.Success(homeFeedPage)

        // Trigger the method to fetch the home feed
        viewModel.fetchHomeFeed()

        // Advance dispatcher to simulate async completion
        testDispatcher.scheduler.advanceUntilIdle()

        // Collect the state flow to observe state changes
        val state = viewModel.state.value

        println("State after fetch: $state")

        // Assert the state after success
        assert(!state.showLoading)
        assert(!state.showError)
        assert(state.homeFeedPage == homeFeedPage)
    }

    @Test
    fun `fetchHomeFeed should update state on error`() = runBlocking {
        // Given an error repository response
        coEvery { repository.getHomeFeed() } returns Resource.Error("Error fetching home feed")

        // Trigger the method to fetch the home feed
        viewModel.fetchHomeFeed()

        // Advance the dispatcher to simulate async completion
        testDispatcher.scheduler.advanceUntilIdle()

        // Collect the state flow to observe state changes
        val state = viewModel.state.value

        println("State after error: $state")

        // Assert the loading state is false after error
        assert(!state.showLoading)
        assert(!state.showError)
        assert(state.homeFeedPage.cards.isEmpty())
    }

    @Test
    fun `fetchHomeFeed should handle no response`() = runBlocking {
        // Given a loading response from the repository
        coEvery { repository.getHomeFeed() } returns Resource.Loading()

        // Trigger the method to fetch the home feed
        viewModel.fetchHomeFeed()

        // Advance the dispatcher to simulate async completion
        testDispatcher.scheduler.advanceUntilIdle()

        // Collect the state flow to observe state changes
        val state = viewModel.state.value

        println("State after loading: $state")

        // Assert that loading state is true initially
        assert(state.showLoading)
        // Assert that we get an error
        assert(!state.showError)

        // Assert that list is empty

        assert(state.homeFeedPage.cards.isEmpty())
    }
}

package com.kudzai.mutswairo.stringsappv2.presentation.viewStrings

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.kudzai.mutswairo.stringsappv2.data.repository.PageRepositoryImpl
import com.kudzai.mutswairo.stringsappv2.presentation.viewStrings.events.StringsScreenEvent
import com.kudzai.mutswairo.stringsappv2.presentation.viewStrings.model.StringsScreenStateData
import com.kudzai.mutswairo.stringsappv2.presentation.viewStrings.viewmodel.StringsViewModel
import com.kudzai.mutswairo.stringsappv2.presentation.viewStrings.viewmodel.UiState
import com.kudzai.mutswairo.stringsappv2.util.Resource
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import java.net.URL

@OptIn(ExperimentalCoroutinesApi::class)
class StringsViewModelTest {
    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    private lateinit var viewModel: StringsViewModel
    private lateinit var repository: PageRepositoryImpl
    private lateinit var mainDispatcher: CoroutineDispatcher
    private val mockUrl = mockk<URL>()

    @Before
    fun setup() {
        MockKAnnotations.init(this)
        repository = PageRepositoryImpl(mockUrl)
        mainDispatcher = Dispatchers.Main
        Dispatchers.setMain(Dispatchers.Unconfined)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `Given getStringData is called, when the repository returns a success, then the result is a success`() =
        runTest {
            val mockRepository = mockk<PageRepositoryImpl>()
            val viewModel = StringsViewModel(mockRepository)

            coEvery { mockRepository.getWebPageFromUrl() } returns Resource.Success("Web page content")

            val resultValues = mutableListOf<UiState<StringsScreenStateData>>()
            backgroundScope.launch(UnconfinedTestDispatcher(testScheduler)) {
                viewModel.uiState.toList(resultValues)
            }

            viewModel.onEvent(StringsScreenEvent.OnGetStrings)

            val expectedState = UiState.Success(
                StringsScreenStateData(
                    tenthChar = "c",
                    everyTenthChar = listOf("c"),
                    uniqueWordsAndCount = mapOf("Web" to 1, "page" to 1, "content" to 1),
                ),
            )

            assertTrue(resultValues[0] is UiState.Loading)
            assertEquals(expectedState, resultValues[1])
        }

    @Test
    fun `Given getStringData is called, when the repository returns a failure, then the result is a failure`() =
        runTest {
            val mockRepository = mockk<PageRepositoryImpl>()
            val viewModel = StringsViewModel(mockRepository)

            coEvery { mockRepository.getWebPageFromUrl() } returns Resource.Error("Error 404: Page not found")

            val resultValues = mutableListOf<UiState<StringsScreenStateData>>()
            backgroundScope.launch(UnconfinedTestDispatcher(testScheduler)) {
                viewModel.uiState.toList(resultValues)
            }

            viewModel.onEvent(StringsScreenEvent.OnGetStrings)

            println(resultValues)
            assertTrue(resultValues[0] is UiState.Loading)
            assertTrue(resultValues[1] is UiState.Error)
        }
}

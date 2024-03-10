package com.tasks.searching.viewmodel

import com.tasks.domain.usecase.DataStoreUseCase
import com.tasks.searching.intent.SearchIntent.ClickSearch
import com.tasks.searching.intent.SearchIntent.ToggleSearchState
import com.tasks.searching.intent.SearchWidgetState.CLOSED
import com.tasks.searching.intent.SearchWidgetState.OPENED
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.setMain
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class SearchBarViewModelTest {
    @ExperimentalCoroutinesApi
    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    private lateinit var dataStoreUseCase: DataStoreUseCase

    private lateinit var viewModel: SearchBarViewModel

    @OptIn(ExperimentalCoroutinesApi::class)
    @Before
    fun setup() {
        dataStoreUseCase = DataStoreUseCase(DataStoreFakeRepository())
        viewModel = SearchBarViewModel(dataStoreUseCase)
        Dispatchers.setMain(StandardTestDispatcher())
    }

    @Test
    fun `save string then read it returns the saved value`(): Unit = runBlocking {
        // Given
        val toBeSaved = "new_value"
        // When
        viewModel.onNewIntent(ClickSearch(toBeSaved))

        // Then
        assertEquals(toBeSaved, viewModel.onNewCity.value)
    }

    @Test
    fun `on NewSearchValue intent, updates searchTextState`(): Unit = runBlocking {
        // Given
        val input = "new_value"
        // When
        viewModel.onNewIntent(ClickSearch(input))

        // Then
        assertEquals(input, viewModel.onNewCity.value)
    }

    @Test
    fun `on ToggleSearchState intent, toggles searchWidgetState`(): Unit = runBlocking {
        // When
        viewModel.onNewIntent(ToggleSearchState)

        // Then
        assertEquals(OPENED, viewModel.searchWidgetState.value)

        // When
        viewModel.onNewIntent(ToggleSearchState)

        // Then
        assertEquals(CLOSED, viewModel.searchWidgetState.value)
    }

}

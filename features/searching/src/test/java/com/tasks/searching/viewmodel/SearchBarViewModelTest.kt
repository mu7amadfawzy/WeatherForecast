package com.tasks.searching.viewmodel

import com.tasks.searching.intent.SearchIntent.NewSearchValue
import com.tasks.searching.intent.SearchIntent.ToggleSearchState
import com.tasks.searching.intent.SearchWidgetState.CLOSED
import com.tasks.searching.intent.SearchWidgetState.OPENED
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Test

class SearchBarViewModelTest {

    @Test
    fun `on NewSearchValue intent, updates searchTextState`(): Unit = runBlocking {
        // Given
        val viewModel = SearchBarViewModel()

        // When
        viewModel.onNewIntent(NewSearchValue("new_value"))

        // Then
        assertEquals("new_value", viewModel.searchTextState.value)
    }

    @Test
    fun `on ToggleSearchState intent, toggles searchWidgetState`(): Unit = runBlocking {
        // Given
        val viewModel = SearchBarViewModel()

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
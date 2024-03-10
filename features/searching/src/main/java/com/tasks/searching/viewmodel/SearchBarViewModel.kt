package com.tasks.searching.viewmodel

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.tasks.searching.intent.SearchIntent
import com.tasks.searching.intent.SearchIntent.NewSearchValue
import com.tasks.searching.intent.SearchIntent.ToggleSearchState
import com.tasks.searching.intent.SearchWidgetState
import com.tasks.searching.intent.SearchWidgetState.CLOSED
import com.tasks.searching.intent.SearchWidgetState.OPENED

class SearchBarViewModel : ViewModel() {
    private val _searchWidgetState: MutableState<SearchWidgetState> =
        mutableStateOf(value = CLOSED)
    val searchWidgetState: State<SearchWidgetState> = _searchWidgetState

    private val _searchTextState: MutableState<String> = mutableStateOf(value = "")
    val searchTextState: State<String> = _searchTextState

    fun onNewIntent(intent: SearchIntent) {
        when (intent) {
            is NewSearchValue -> updateSearchTextState(intent.input)

            is ToggleSearchState -> toggleSearchWidget()
        }
    }

    private fun toggleSearchWidget() {
        when (searchWidgetState.value) {
            OPENED -> updateSearchWidgetState(CLOSED)

            CLOSED -> updateSearchWidgetState(OPENED)
        }
    }

    private fun updateSearchWidgetState(newValue: SearchWidgetState) {
        _searchWidgetState.value = newValue
    }

    private fun updateSearchTextState(newValue: String) {
        _searchTextState.value = newValue
    }
}

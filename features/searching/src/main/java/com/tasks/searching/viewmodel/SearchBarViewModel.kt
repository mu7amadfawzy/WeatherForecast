package com.tasks.searching.viewmodel

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tasks.domain.usecase.DataStoreUseCase
import com.tasks.searching.intent.SearchIntent
import com.tasks.searching.intent.SearchIntent.ClickSearch
import com.tasks.searching.intent.SearchIntent.ToggleSearchState
import com.tasks.searching.intent.SearchWidgetState
import com.tasks.searching.intent.SearchWidgetState.CLOSED
import com.tasks.searching.intent.SearchWidgetState.OPENED
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SearchBarViewModel @Inject constructor(
    private val storeUseCase: DataStoreUseCase
) : ViewModel() {
    private val _searchWidgetState: MutableState<SearchWidgetState> =
        mutableStateOf(value = CLOSED)
    val searchWidgetState: State<SearchWidgetState> = _searchWidgetState

    private var _onNewCity: MutableState<String?> = mutableStateOf(null)
    val onNewCity: State<String?> = _onNewCity

    init {
        viewModelScope.launch {
            storeUseCase.read(SEARCH_INPUT)?.let {
                _onNewCity.value = it
            }
        }
    }

    fun onNewIntent(intent: SearchIntent) {
        when (intent) {
            is ClickSearch -> saveSearchValue(intent.input)

            is ToggleSearchState -> toggleSearchWidget()
        }
    }

    private fun toggleSearchWidget() {
        when (searchWidgetState.value) {
            OPENED -> updateSearchWidgetState(CLOSED)

            CLOSED -> updateSearchWidgetState(OPENED)
        }
    }

    private fun saveSearchValue(input: String) {
        if (input.isEmpty()) return
        _onNewCity.value = input
        viewModelScope.launch {
            storeUseCase.save(SEARCH_INPUT, input)
        }
    }

    private fun updateSearchWidgetState(newValue: SearchWidgetState) {
        _searchWidgetState.value = newValue
    }
}

private const val SEARCH_INPUT = "NewCity"




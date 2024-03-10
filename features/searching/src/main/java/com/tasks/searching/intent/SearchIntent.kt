package com.tasks.searching.intent

sealed class SearchIntent {
    data object ToggleSearchState : SearchIntent()
    data class ClickSearch(val input: String) : SearchIntent()
}

enum class SearchWidgetState {
    OPENED,
    CLOSED
}
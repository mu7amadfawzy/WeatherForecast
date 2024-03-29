package com.tasks.searching.ui

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.tasks.searching.intent.SearchIntent.ClickSearch
import com.tasks.searching.intent.SearchIntent.ToggleSearchState
import com.tasks.searching.intent.SearchWidgetState
import com.tasks.searching.viewmodel.SearchBarViewModel

@Composable
fun WeatherTopAppBar(
    title: String,
    onSearchClicked: (String) -> Unit
) {
    val viewModel: SearchBarViewModel = hiltViewModel()
    val searchWidgetState by viewModel.searchWidgetState
    val newCitySearch by viewModel.onNewCity

    LaunchedEffect(newCitySearch) {
        newCitySearch?.let {
            onSearchClicked(it)
        }
    }

    when (searchWidgetState) {
        SearchWidgetState.CLOSED ->
            DefaultAppBar(
                title = title,
                onSearchClicked = {
                    viewModel.onNewIntent(ToggleSearchState)
                }
            )

        SearchWidgetState.OPENED ->
            SearchAppBar(
                onCloseClicked = { viewModel.onNewIntent(ToggleSearchState) },
                onSearchClicked = {
                    viewModel.onNewIntent(ClickSearch(it))
                }
            )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DefaultAppBar(
    title: String,
    onSearchClicked: () -> Unit
) {
    TopAppBar(
        title = {
            Text(
                text = title,
                fontWeight = FontWeight.Bold,
            )
        },
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = colorScheme.primaryContainer,
            titleContentColor = colorScheme.onBackground,
        ),
        actions = {
            IconButton(
                onClick = { onSearchClicked() }
            ) {
                Icon(
                    imageVector = Icons.Filled.Search,
                    contentDescription = "search_icon",
                )
            }
        }
    )
}

@Composable
fun SearchAppBar(
    onCloseClicked: () -> Unit,
    onSearchClicked: (String) -> Unit,
) {
    val keyboardController = LocalSoftwareKeyboardController.current
    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .height(64.dp),
        color = colorScheme.primary,
    ) {
        var searchTextState by remember { mutableStateOf("") }
        TextField(
            modifier = Modifier.fillMaxWidth(),
            value = searchTextState,
            onValueChange = { searchTextState = it },
            placeholder = {
                Text(
                    modifier = Modifier.alpha(0.5f),
                    text = "type a City name and click search..",
                )
            },
            textStyle = TextStyle(
                fontSize = MaterialTheme.typography.titleMedium.fontSize
            ),
            singleLine = true,
            leadingIcon = {
                IconButton(
                    modifier = Modifier.alpha(0.7f),
                    onClick = {}
                ) {
                    Icon(
                        imageVector = Icons.Default.Search,
                        contentDescription = "search_icon",
                    )
                }
            },
            trailingIcon = {
                IconButton(
                    onClick = {
                        if (searchTextState.isNotEmpty())
                            searchTextState = ""
                        else
                            onCloseClicked()
                    }
                ) {
                    Icon(
                        imageVector = Icons.Default.Close,
                        contentDescription = "close_icon",
                    )
                }
            },
            keyboardOptions = KeyboardOptions(
                imeAction = ImeAction.Search
            ),
            keyboardActions = KeyboardActions(
                onSearch = {
                    onSearchClicked(searchTextState)
                    keyboardController?.hide()
                },
            ),
        )
    }
}
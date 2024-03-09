package com.tasks.currentweather.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.BasicAlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ErrorDialog(message: String?, onRetry: () -> Unit) {
    val openDialog = remember { mutableStateOf(true) }

    if (openDialog.value) {
        BasicAlertDialog(
            onDismissRequest = {
                openDialog.value = false
                onRetry()
            },
            content = {
                DialogContent(message, openDialog, onRetry)
            })
    }
}

@Composable
private fun DialogContent(
    message: String?,
    openDialog: MutableState<Boolean>,
    onRetry: () -> Unit
) {
    Card {
        Column(
            Modifier.padding(20.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "An unexpected error occurred",
                color = colorScheme.primary,
                fontStyle = FontStyle.Italic
            )
            Spacer(Modifier.height(10.dp))
            message?.let {
                Text(it, color = colorScheme.onBackground)
            }
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(12.dp),
                horizontalArrangement = Arrangement.SpaceEvenly,
            ) {
                Button(modifier = Modifier,
                    content = {
                        Text(text = " Retry ", color = colorScheme.onPrimary)
                    },
                    onClick = {
                        openDialog.value = false
                        onRetry()
                    })
                Button(modifier = Modifier,
                    content = {
                        Text(text = "Dismiss", color = colorScheme.onSecondary)
                    },
                    onClick = {
                        openDialog.value = false
                    })
            }
        }
    }
}
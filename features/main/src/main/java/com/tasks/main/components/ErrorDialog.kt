package com.tasks.main.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.BasicAlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ErrorDialog(message: String?, onDismiss: () -> Unit) {
    val openDialog = remember { mutableStateOf(true) }

    if (openDialog.value) {
        BasicAlertDialog(
            onDismissRequest = {
                openDialog.value = false
                onDismiss()
            },
            content = {
                DialogContent(message, openDialog, onDismiss)
            })
    }
}

@Composable
private fun DialogContent(
    message: String?,
    openDialog: MutableState<Boolean>,
    onDismiss: () -> Unit
) {
    Column {
        Text(
            text = "An unexpected error occurred",
            color = Color.Black
        )
        message?.let {
            Text(it, color = Color.Black)
        }
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp),
            horizontalArrangement = Arrangement.Center
        ) {
            Button(modifier = Modifier,
                content = {
                    Text(text = "Dismiss", color = colorScheme.onBackground)
                },
                onClick = {
                    openDialog.value = false
                    onDismiss()
                })

        }
    }
}
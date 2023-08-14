package com.kudzai.mutswairo.stringsappv2.presentation.components

import androidx.compose.material3.Snackbar
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable

@Composable
fun ErrorSnackbar(
    snackbarHostState: SnackbarHostState,
    message: String,
    actionLabel: String? = null,
    onActionClick: (() -> Unit)? = null,
) {
    SnackbarHost(
        hostState = snackbarHostState,
        snackbar = {
            Snackbar(
                action = {onActionClick?.invoke()},
            ) {
                Text(text = message)
            }
        },
    )
}

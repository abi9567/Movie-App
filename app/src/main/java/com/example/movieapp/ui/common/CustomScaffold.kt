package com.example.movieapp.ui.common

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
fun CustomScaffold(
    modifier : Modifier = Modifier,
    topBar: @Composable (() -> Unit) = {},
    content : @Composable (PaddingValues) -> Unit
) {
    Scaffold(modifier = modifier,
        topBar = topBar) { customScaffoldPadding ->
        content(customScaffoldPadding)
    }
}
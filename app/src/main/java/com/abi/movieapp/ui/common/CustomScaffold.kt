package com.abi.movieapp.ui.common

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color

@Composable
fun CustomScaffold(
    modifier : Modifier = Modifier,
    topBar: @Composable (() -> Unit) = {},
    content : @Composable (PaddingValues) -> Unit
) {
    Scaffold(modifier = modifier,
        containerColor = Color.Transparent,
        topBar = topBar) { customScaffoldPadding ->
        content(customScaffoldPadding)
    }
}
package com.example.movieapp.ui.common

import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.material.ExperimentalMaterialApi
//noinspection UsingMaterialAndMaterial3Libraries
import androidx.compose.material.ModalBottomSheetLayout
//noinspection UsingMaterialAndMaterial3Libraries
import androidx.compose.material.ModalBottomSheetState
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Shape
import com.example.movieapp.ui.theme.BottomSheetBackgroundColor

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun CustomModalBottomSheetLayout(
    sheetContent : @Composable (ColumnScope) -> Unit,
    sheetState : ModalBottomSheetState,
    shape : Shape = MaterialTheme.shapes.medium,
    content : @Composable () -> Unit,
) {
    ModalBottomSheetLayout(sheetContent = sheetContent,
        sheetState = sheetState,
        sheetBackgroundColor = BottomSheetBackgroundColor,
        sheetShape = shape
    ) { content() }
}
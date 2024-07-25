package com.abi.movieapp.ui.common

import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.ModalBottomSheetLayout
import androidx.compose.material.ModalBottomSheetState
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Shape
import com.abi.movieapp.ui.theme.BottomSheetBackgroundColor

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun CustomModalBottomSheetLayout(
    sheetContent : @Composable (ColumnScope) -> Unit,
    sheetState : ModalBottomSheetState,
    shape : Shape = MaterialTheme.shapes.medium,
    isSheetGesturesEnabled : Boolean = true,
    content : @Composable () -> Unit,
) {
    ModalBottomSheetLayout(sheetContent = sheetContent,
        sheetState = sheetState,
        sheetBackgroundColor = BottomSheetBackgroundColor,
        sheetGesturesEnabled = isSheetGesturesEnabled,
        sheetShape = shape
    ) { content() }
}
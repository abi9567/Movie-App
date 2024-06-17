package com.example.movieapp.ui.common

import androidx.annotation.DimenRes
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource

@Composable
fun CustomWidthSpacer(@DimenRes dimenResId : Int) {
    Spacer(modifier = Modifier.width(width = dimensionResource(id = dimenResId)))
}

@Composable
fun CustomHeightSpacer(@DimenRes dimenResId : Int) {
    Spacer(modifier = Modifier.height(height = dimensionResource(id = dimenResId)))
}
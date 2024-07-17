package com.abi.movieapp.internal.extensions

import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.sp

fun Int?.calculateDurationTime() : String {
    if (this == null) return "0 hr"
    val quotient = this / 60
    val reminder = this % 60
    return "$quotient h $reminder m"
}

@Composable
fun Int.fixedTextSize() : TextUnit {
    val value : Int = this
    return with(LocalDensity.current) {
        val fontScale = this.fontScale
        val textSize = value / fontScale
        textSize.sp
    }
}
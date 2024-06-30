package com.example.movieapp.ui.theme

import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color

val AppBackgroundColor = Color(0xFF1A2232)
val SecondaryLightColor = Color(0xFF637394)
val YellowColor = Color(0xFFFCF8A7)
val PrimaryColor = Color.White
val PrimaryGradientColor = Color(0xFFFF8036)
val GradientColorSecond = Color(0xFFFC6D19)
val MidnightBlue = Color(0xFF1F293D)
val TextFieldBorderColor = Color(0xFF6D9EFF).copy(alpha = 0.10F)
val ButtonGradientBrush = Brush.linearGradient(colors = listOf(PrimaryGradientColor, GradientColorSecond))
val RatingGradientBrush = Brush.verticalGradient(colors = listOf(MidnightBlue.copy(alpha = 0F), MidnightBlue))
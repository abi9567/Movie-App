package com.example.movieapp.ui.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.example.movieapp.R

private val fontFamily = FontFamily(
    listOf(
        Font(resId = R.font.pt_root_regular, weight = FontWeight.Normal),
        Font(resId = R.font.pt_root_bold, weight = FontWeight.Bold),
        Font(resId = R.font.pt_root_light, weight = FontWeight.Light),
        Font(resId = R.font.pt_root_medium, weight = FontWeight.Medium)))

val Typography = Typography(

    displayLarge = TextStyle(
        fontFamily = fontFamily,
        fontSize = 24.sp,
        color = Color.White,
        fontWeight = FontWeight.Bold),

    displayMedium = TextStyle(
        fontFamily = fontFamily,
        fontSize = 24.sp,
        color = Color.White,
        fontWeight = FontWeight.Normal),

    displaySmall = TextStyle(
        fontFamily = fontFamily,
        fontSize = 20.sp,
        color = Color.White,
        fontWeight = FontWeight.Bold),

    headlineLarge = TextStyle(
        fontFamily = fontFamily,
        fontSize = 20.sp,
        color = Color.White,
        fontWeight = FontWeight.Normal),

    headlineMedium = TextStyle(
        fontFamily = fontFamily,
        fontSize = 18.sp,
        color = Color.White,
        fontWeight = FontWeight.Bold),

    headlineSmall = TextStyle(
        fontFamily = fontFamily,
        fontSize = 18.sp,
        color = Color.White,
        fontWeight = FontWeight.Normal),

    titleLarge = TextStyle(
        fontFamily = fontFamily,
        fontSize = 16.sp,
        color = Color.White,
        fontWeight = FontWeight.Bold),

    titleMedium = TextStyle(
        fontFamily = fontFamily,
        fontSize = 16.sp,
        color = Color.White,
        fontWeight = FontWeight.Normal),

    titleSmall = TextStyle(
        fontFamily = fontFamily,
        fontSize = 14.sp,
        color = Color.White,
        fontWeight = FontWeight.Bold),

     bodyLarge = TextStyle(
        fontFamily = fontFamily,
        fontSize = 14.sp,
         color = Color.White,
        fontWeight = FontWeight.Normal),

     bodyMedium = TextStyle(
        fontFamily = fontFamily,
        fontSize = 12.sp,
         color = Color.White,
        fontWeight = FontWeight.Bold),

     bodySmall = TextStyle(
        fontFamily = fontFamily,
        fontSize = 12.sp,
         color = Color.White,
        fontWeight = FontWeight.Normal),

     labelLarge = TextStyle(
        fontFamily = fontFamily,
        fontSize = 10.sp,
         color = Color.White,
        fontWeight = FontWeight.Bold),

     labelMedium = TextStyle(
        fontFamily = fontFamily,
        fontSize = 10.sp,
         color = Color.White,
        fontWeight = FontWeight.Normal)
)
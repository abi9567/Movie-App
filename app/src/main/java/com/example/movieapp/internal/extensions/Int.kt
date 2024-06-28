package com.example.movieapp.internal.extensions

fun Int?.calculateDurationTime() : String {
    if (this == null) return "0 hr"
    val quotient = this / 60
    val reminder = this % 60
    return "$quotient h $reminder m"
}
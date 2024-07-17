package com.abi.movieapp.internal.extensions

import java.text.DecimalFormat

fun Double?.formatMovieRating() : String {
    if (this == null) return "0.0"
    val decimalFormat = DecimalFormat("0.0")
    return decimalFormat.format(this)
}

fun Double?.calculateTimeInHour() : String {
    if (this == null) return "0"
    return "${ this.div(other = 60.0).formatMovieRating() } hr"
}
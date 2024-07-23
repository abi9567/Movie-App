package com.abi.movieapp.internal.extensions

import androidx.navigation.NavController

fun NavController.navigateWithPopUpToInclusive(route : String, inclusiveRoute : String? = null) {
    this.navigate(route = route) {
        popUpTo(route = inclusiveRoute ?: route) {
            inclusive = true
        }
    }
}
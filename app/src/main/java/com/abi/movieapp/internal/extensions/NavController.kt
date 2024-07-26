package com.abi.movieapp.internal.extensions

import androidx.navigation.NavController
import com.google.gson.Gson

fun NavController.navigateWithPopUpToInclusive(route : String, inclusiveRoute : String? = null) {
    this.navigate(route = route) {
        popUpTo(route = inclusiveRoute ?: route) {
            inclusive = true
        }
    }
}

fun <T> NavController.navigateAndSendData(
    route : String,
    key : String,
    data : T?
) {
    val gson = Gson()
    val stringData = gson.toJson(data)
    this.navigate(route = route)
    this.currentBackStackEntry?.savedStateHandle?.set(key = key, value = stringData)
}

inline fun <reified T> NavController.getDataFromBackStackEntry(key : String) : T? {
    val gson = Gson()
    val stringData = this.currentBackStackEntry?.savedStateHandle?.get<String?>(key = key)
    stringData?.let {
        return gson.fromJson(stringData, T::class.java)
    }
    return null
}

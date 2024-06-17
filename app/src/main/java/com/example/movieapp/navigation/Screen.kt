package com.example.movieapp.navigation

sealed class Screen(val route : String) {

    data object SplashScreen : Screen(route = "splash_screen")
    data object HomeScreen : Screen(route = "home_screen")

}
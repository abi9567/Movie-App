package com.example.movieapp.navigation

sealed class Screen(val route : String) {

    data object SplashScreen : Screen(route = "splash_screen")
    data object HomeScreen : Screen(route = "home_screen")
    data object DetailScreen : Screen(route = "detail_screen/{${MOVIE_ID}}") {
        fun detailScreenArgs(movieId : Int?) = "detail_screen/$movieId"
    }

    companion object Args {
        const val MOVIE_ID = "movie_id"
        const val YOUTUBE = "YouTube"
    }

}
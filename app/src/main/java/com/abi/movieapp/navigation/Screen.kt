package com.abi.movieapp.navigation

sealed class Screen(val route : String) {

    data object SplashScreen : Screen(route = "splash_screen")
    data object HomeScreen : Screen(route = "home_screen")
    data object DetailScreen : Screen(route = "detail_screen/{$MOVIE_ID}") {
        fun detailScreenArgs(movieId : Int?) = "detail_screen/$movieId"
    }
    data object ActorDetailScreen : Screen(route = "actor_detail_screen/{$ACTOR_ID}") {
        fun actorDetailScreen(actorId : Int?) = "actor_detail_screen/$actorId"
    }
    data object SearchScreen : Screen(route = "search_screen")
    data object SeatSelectionScreen : Screen(route = "seat_selection_screen")
    data object PayTicketScreen : Screen(route = "pay_ticket_screen")

    companion object Args {
        const val MOVIE_ID = "movie_id"
        const val ACTOR_ID = "actor_id"
        const val YOUTUBE = "YouTube"
        const val BOOKING_DETAIL = "booking_detail"
    }

}
package com.abi.movieapp.data.response

data class MovieShowTime(
    val theatreList : List<Theatre?>?
) {
    companion object {
        val showTimeList = Theatre.showTime
    }
}

data class Theatre(
    val name : String?,
    val distance : Double?,
    val showTime : List<String?>?
) {
    companion object {
        val showTime = listOf(
            Theatre(name = "PVS Film City", distance = 1.5, showTime = listOf("4:00 PM", "7:00 PM", "10:00 PM")),
            Theatre(name = "Regal Cinema", distance = 0.5, showTime = listOf("11:00 AM", "2:00 PM", "5:00 PM", "8:00 PM")),
            Theatre(name = "Crown Theatre", distance = 3.0, showTime = listOf("9:30 AM", "12:30 PM", "3:30 PM", "6:30 PM", "9:30 PM")),
            Theatre(name = "Film City Mall", distance = 4.0, showTime = listOf("10:15 AM", "1:15 PM", "4:15 PM", "7:15 PM", "10:15 PM")),
            Theatre(name = "Kairali Theatre", distance = 1.1, showTime = listOf("9:45 AM", "12:45 PM", "3:45 PM", "6:45 PM", "9:45 PM")),
            Theatre(name = "Radha Theatre", distance = 1.6, showTime = listOf("11:00 AM", "2:00 PM", "5:00 PM", "8:00 PM")),
            Theatre(name = "Coronation Theatre", distance = 2.0, showTime = listOf("10:30 AM", "1:30 PM", "4:30 PM", "7:30 PM", "10:30 PM")),
            Theatre(name = "Cinepolis", distance = 0.7, showTime = listOf("10:00 AM", "1:00 PM", "4:00 PM", "7:00 PM", "10:00 PM")),
            Theatre(name = "HiLITE Mall Cinemas", distance = 9.0, showTime = listOf("11:30 AM", "2:30 PM", "5:30 PM", "8:30 PM")),
            Theatre(name = "Carnival Cinemas", distance = 8.0, showTime = listOf("10:45 AM", "1:45 PM", "4:45 PM", "7:45 PM", "10:45 PM")),
        )
    }
}

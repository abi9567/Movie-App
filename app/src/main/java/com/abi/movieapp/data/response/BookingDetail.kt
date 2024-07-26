package com.abi.movieapp.data.response

data class BookingDetail(
    val filmName : String? = null,
    val selectedDate : Pair<String, String>? = null,
    val selectedTime : String? = null,
    val bookingAmount : Int? = null,
    val filmTheatreName : String? = null,
    val availableTimeSlotList : List<String?>? = null,
    val selectedSeatList : List<Seat?>? = null
) {
    val updatedTimeString : String?
        get() = selectedSeatList?.joinToString { "${it?.seatNumber}" }
}

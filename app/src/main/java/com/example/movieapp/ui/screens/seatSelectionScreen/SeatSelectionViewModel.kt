package com.example.movieapp.ui.screens.seatSelectionScreen

import androidx.lifecycle.ViewModel
import com.example.movieapp.data.response.Seat
import com.example.movieapp.data.response.TheatreHall
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow

class SeatSelectionViewModel : ViewModel() {

    val theatreHallSeatList = TheatreHall.theatreSeatsAvailable

    private val _selectedSeats = MutableStateFlow<MutableList<Seat?>?>(value = null)
    val selectedSeats : Flow<MutableList<Seat?>?> = _selectedSeats

    private val _totalSeatsToBeBooked = MutableStateFlow(value = 1)
    val totalSeatsToBeBooked : Flow<Int> = _totalSeatsToBeBooked

    private val _remainingSeatsToBeBooked = MutableStateFlow(value = 1)
    val remainingSeatsToBeBooked : Flow<Int> = _remainingSeatsToBeBooked

    init {
        setTotalSeatsToBeBooked(count = 5)
    }

    fun addOrRemoveSeat(item : Seat?) {
        val isSeatSelected = _selectedSeats.value?.contains(element = item) ?: false

        if (isSeatSelected) {
            removeSeat(item = item)
            return
        }
        addSeat(item = item)
    }

    private fun addSeat(item : Seat?) {
        val currentSelectedSeatList = _selectedSeats.value?.toMutableList() ?: mutableListOf()

        val key = item?.seatNumber?.first().toString()
        val fullSeatListForKey = theatreHallSeatList.layout.get(key = key)
        val currentSeatPositionInTheList = fullSeatListForKey?.indexOf(element = item) ?: 0

        val seatsToBeAdded = availableSeatsForBooking(
            seatListForKey = fullSeatListForKey,
            fromIndex = currentSeatPositionInTheList,
            toIndex = currentSeatPositionInTheList + _totalSeatsToBeBooked.value)

        currentSelectedSeatList.addAll(seatsToBeAdded)
        _selectedSeats.value = currentSelectedSeatList
    }

    private fun removeSeat(item : Seat?) {
        val currentList = _selectedSeats.value?.toMutableList()?.apply {
            remove(element = item)
        }
        setTotalSeatsToBeBooked(count = _totalSeatsToBeBooked.value.minus(other = currentList?.size ?: 0))
        _selectedSeats.value = currentList
    }

    private fun setTotalSeatsToBeBooked(count : Int) {
        _totalSeatsToBeBooked.value = count
    }

    private fun availableSeatsForBooking(
        seatListForKey : List<Seat?>?,
        fromIndex : Int,
        toIndex : Int
    ) : MutableList<Seat?> {

        val validToIndex = toIndex.coerceAtMost(maximumValue = seatListForKey?.size ?: 0)
        val subListFromCurrentSeatToNextSeats = seatListForKey?.subList(fromIndex = fromIndex, toIndex = validToIndex)
        if (subListFromCurrentSeatToNextSeats.isNullOrEmpty()) return mutableListOf()

        val tempList = mutableSetOf<Seat?>()
        for (item in subListFromCurrentSeatToNextSeats) {
            if (item?.available == true) {
                tempList.add(element = item)
            } else {
                break
            }
        }
        setTotalSeatsToBeBooked(count = _totalSeatsToBeBooked.value.minus(other = tempList.size))
        return tempList.toMutableList()
    }
}
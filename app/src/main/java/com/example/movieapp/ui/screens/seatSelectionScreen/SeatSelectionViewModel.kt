package com.example.movieapp.ui.screens.seatSelectionScreen

import androidx.lifecycle.ViewModel
import com.example.movieapp.data.response.Seat
import com.example.movieapp.data.response.TheatreHall
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow

class SeatSelectionViewModel : ViewModel() {

    val theatreHallSeatList = TheatreHall.theatreSeatsAvailable

    private val _selectedSeats = MutableStateFlow<MutableSet<Seat?>?>(value = null)
    val selectedSeats : Flow<MutableSet<Seat?>?> = _selectedSeats

    private val _totalSeatsToBeBooked = MutableStateFlow(value = 1)
    val totalSeatsToBeBooked : Flow<Int> = _totalSeatsToBeBooked

    private val _remainingSeatsToBeBooked = MutableStateFlow(value = 0)

    private val _totalAmount = MutableStateFlow<Int?>(value = null)
    val totalAmount : Flow<Int?> = _totalAmount

    init {
        setTotalSeatsToBeBooked(count = 1)
    }

    fun addOrRemoveSeat(item : Seat?) {
        val isSeatSelected = _selectedSeats.value?.contains(element = item) ?: false

        if (isSeatSelected) {
            removeSeat(item = item)
            return
        }
        addSeat(item = item)
    }

    private fun clearSelectedSeats() {
        _selectedSeats.value = null
        _remainingSeatsToBeBooked.value = _totalSeatsToBeBooked.value
        _totalAmount.value = null
    }

    private fun addSeat(item : Seat?) {
        if (_selectedSeats.value?.size == _totalSeatsToBeBooked.value) clearSelectedSeats()
        val currentSelectedSeatList = _selectedSeats.value ?: mutableSetOf()

        val key = item?.seatNumber?.first().toString()
        val fullSeatListForKey = theatreHallSeatList.layout.get(key = key)
        val currentSeatPositionInTheList = fullSeatListForKey?.indexOf(element = item) ?: 0

        val seatsToBeAdded = availableSeatsForBooking(
            seatListForKey = fullSeatListForKey,
            fromIndex = currentSeatPositionInTheList,
            toIndex = currentSeatPositionInTheList + _remainingSeatsToBeBooked.value)

        currentSelectedSeatList.addAll(seatsToBeAdded)
        _selectedSeats.value = currentSelectedSeatList
        calculateBookingAmount()
        setRemainingSeatsToBeBooked(countTobeMinus = seatsToBeAdded.size)
    }

    private fun calculateBookingAmount() {
        var tempAmount : Int? = null
        _selectedSeats.value?.forEach { seat ->
            tempAmount = (tempAmount ?: 0) + (seat?.price ?: 0)
        }
        _totalAmount.value = tempAmount
    }

    private fun removeSeat(item : Seat?) {
        val currentList = _selectedSeats.value?.apply {
            remove(element = item)
        }
        setRemainingSeatsToBeBooked(countTobeMinus = -1)
        _selectedSeats.value = currentList
        calculateBookingAmount()
    }

    fun setTotalSeatsToBeBooked(count : Int, resetSeatCount : Boolean = false) {
        _totalSeatsToBeBooked.value = count
        if (resetSeatCount) {
            clearSelectedSeats()
            return
        }
        setRemainingSeatsToBeBooked(countTobeMinus = -count)
    }

    private fun setRemainingSeatsToBeBooked(countTobeMinus : Int) {
        _remainingSeatsToBeBooked.value -= countTobeMinus
    }

    private fun availableSeatsForBooking(
        seatListForKey : List<Seat?>?,
        fromIndex : Int,
        toIndex : Int
    ) : MutableSet<Seat?> {

        val validToIndex = toIndex.coerceAtMost(maximumValue = seatListForKey?.size ?: 0)
        val subListFromCurrentSeatToNextSeats = seatListForKey?.subList(fromIndex = fromIndex, toIndex = validToIndex)
        if (subListFromCurrentSeatToNextSeats.isNullOrEmpty()) return mutableSetOf()

        val tempList = mutableSetOf<Seat?>()
        for (item in subListFromCurrentSeatToNextSeats) {
            if (item?.available == true) {
                tempList.add(element = item)
            } else {
                break
            }
        }

        return tempList
    }
}
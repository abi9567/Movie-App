package com.abi.movieapp.ui.screens.seatSelectionScreen

import androidx.lifecycle.ViewModel
import com.abi.movieapp.data.response.Seat
import com.abi.movieapp.data.response.TheatreHall
import com.abi.movieapp.internal.enums.DateSelectionEnum
import com.abi.movieapp.utils.other.Utils
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import java.util.Calendar

class SeatSelectionViewModel : ViewModel() {

    val theatreHallSeatList = TheatreHall.theatreSeatsAvailable

    private val _selectedSeats = MutableStateFlow<MutableSet<Seat?>?>(value = null)
    val selectedSeats : Flow<MutableSet<Seat?>?> = _selectedSeats

    private val _totalSeatsToBeBooked = MutableStateFlow(value = 2)
    val totalSeatsToBeBooked : Flow<Int> = _totalSeatsToBeBooked

    private val _remainingSeatsToBeBooked = MutableStateFlow(value = 0)

    private val _totalAmount = MutableStateFlow<Int?>(value = null)
    val totalAmount : Flow<Int?> = _totalAmount

    private val _currentPickerSelection = MutableStateFlow(value = DateSelectionEnum.NotVisible)
    val currentPickerSelection : Flow<DateSelectionEnum> = _currentPickerSelection

    private val calendar = Calendar.getInstance()
    private val time = calendar.time.time
    private val calculationValue : Long = 86400000
    private val nextDaysListInMilliSecond = listOf(
        (time + 0 * calculationValue),
        (time + 1 * calculationValue),
        (time + 2 * calculationValue),
        (time + 3 * calculationValue),
        (time + 4 * calculationValue),
        (time + 5 * calculationValue),
        (time + 6 * calculationValue),
        (time + 7 * calculationValue),
        (time + 8 * calculationValue),
        (time + 9 * calculationValue),
    )

    val nextDaysList = nextDaysListInMilliSecond.map { Utils.convertToDate(time = it) }
    val timeList = listOf("10 AM", "1 PM", "4 PM", "6 PM", "8 PM", "11 PM")

    private val _selectedDate = MutableStateFlow(nextDaysList.get(index = 0))
    val selectedDate : Flow<Pair<String, String>> = _selectedDate

    private val _selectedTime = MutableStateFlow(timeList.get(index = 0))
    val selectedTime : Flow<String> = _selectedTime

    init {
        setTotalSeatsToBeBooked(count = 2)
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

    fun setDate(date : Pair<String, String>) {
        _selectedDate.value = date
        setPicker(item = DateSelectionEnum.NotVisible)
    }

    fun setTime(time : String) {
        _selectedTime.value = time
        setPicker(item = DateSelectionEnum.NotVisible)
    }

    fun setPicker(item : DateSelectionEnum) {
        if (_currentPickerSelection.value == item) {
            _currentPickerSelection.value = DateSelectionEnum.NotVisible
            return
        }
        _currentPickerSelection.value = item
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
package com.abi.movieapp.ui.screens.homeScreen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import com.abi.movieapp.repository.Repository
import com.abi.movieapp.utils.other.Utils
import dagger.hilt.android.lifecycle.HiltViewModel
import java.util.Calendar
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(private val repository: Repository) : ViewModel() {

    private val calendar = Calendar.getInstance()
    private val todayDate = calendar.time
    private val todayDateFormated = Utils.formatTodayDate(date = todayDate)

    val filmList = repository.getNowPlayingMovies(releaseDate = todayDateFormated)
        .cachedIn(scope = viewModelScope)

}
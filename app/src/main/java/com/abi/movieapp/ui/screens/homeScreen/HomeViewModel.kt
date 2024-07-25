package com.abi.movieapp.ui.screens.homeScreen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asFlow
import androidx.lifecycle.asLiveData
import androidx.lifecycle.switchMap
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import com.abi.movieapp.internal.enums.Language
import com.abi.movieapp.repository.Repository
import com.abi.movieapp.utils.other.DataStoreUtil
import com.abi.movieapp.utils.other.Utils
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import java.util.Calendar
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(repository: Repository,
                                        private val dataStore : DataStoreUtil) : ViewModel() {

    private val calendar = Calendar.getInstance()
    private val todayDate = calendar.time
    private val todayDateFormated = Utils.formatTodayDate(date = todayDate)
    val languageList = Language.entries.toList()
    val currentLanguage = dataStore.movieLanguage.asLiveData()

    val filmList = currentLanguage.switchMap { lang ->
        repository.getNowPlayingMovies(releaseDate = todayDateFormated,
            language = Utils.selectedDateStringValue(language = lang)
        ).asLiveData()
    }.asFlow().cachedIn(scope = viewModelScope)

    fun changeLanguage(language: Language) {
        viewModelScope.launch {
            dataStore.setMovieLanguage(language = language)
        }
    }
}
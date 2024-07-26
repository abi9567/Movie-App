package com.abi.movieapp.ui.screens.detailScreen

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.abi.movieapp.data.response.BookingDetail
import com.abi.movieapp.data.response.Cast
import com.abi.movieapp.data.response.Comment
import com.abi.movieapp.data.response.CommonPagingResponse
import com.abi.movieapp.data.response.MovieDetail
import com.abi.movieapp.data.response.Movie
import com.abi.movieapp.data.response.MovieShowTime
import com.abi.movieapp.data.response.Resource
import com.abi.movieapp.data.response.Theatre
import com.abi.movieapp.data.response.Video
import com.abi.movieapp.internal.enums.DetailScreenTabs
import com.abi.movieapp.navigation.Screen
import com.abi.movieapp.repository.Repository
import com.abi.movieapp.utils.other.Utils
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailViewModel @Inject constructor(private val repository : Repository,
    savedStateHandle : SavedStateHandle
    ) : ViewModel() {

    private val _selectedTab = MutableStateFlow(value = DetailScreenTabs.About)
    val selectedTab : Flow<DetailScreenTabs> = _selectedTab

    private val _movieDetail = MutableStateFlow<Resource<MovieDetail>?>(null)
    val movieDetail : Flow<Resource<MovieDetail>?> = _movieDetail

    private val _recommendations = MutableStateFlow<Resource<CommonPagingResponse<Movie>>?>(null)
    val recommendations : Flow<Resource<CommonPagingResponse<Movie>>?> = _recommendations

    private val _credits = MutableStateFlow<Resource<Cast>?>(null)
    val credits : Flow<Resource<Cast>?> = _credits

    private val _reviews = MutableStateFlow<Resource<CommonPagingResponse<Comment>>?>(null)
    val reviews : Flow<Resource<CommonPagingResponse<Comment>>?> = _reviews

    private val _youtubeUrls = MutableStateFlow<Resource<CommonPagingResponse<Video>>?>(null)
    val youtubeUrls : Flow<Resource<CommonPagingResponse<Video>>?> = _youtubeUrls

    private val movieId = savedStateHandle.get<String?>(Screen.MOVIE_ID)
//    private val movieId = "1022789"

    private val _isSortedByDistance = MutableStateFlow(value = false)
    val isSortedByDistance : Flow<Boolean> = _isSortedByDistance

    val tabList = listOf(DetailScreenTabs.About, DetailScreenTabs.Booking)

    private val initialMovieList = MovieShowTime.showTimeList

    private val _movieShowTime = MutableStateFlow(value = initialMovieList)
    val movieShowTime : Flow<List<Theatre>> = _movieShowTime

    val nextDaysList = Utils.getNextDaysList()

    private val _selectedDate = MutableStateFlow(nextDaysList.get(index = 0))
    val selectedDate : Flow<Pair<String, String>> = _selectedDate

    private val _isDateVisible = MutableStateFlow(value = false)
    val isDateVisible : Flow<Boolean> = _isDateVisible


    init {
        fetchMovieDetail()
        fetchRecommendations()
        getCredits()
        getComments()
        getYoutubeUrl()
    }

    fun setTab(tab : DetailScreenTabs) {
        if (_selectedTab.value == tab) return
        _selectedTab.value = tab
    }

    fun setDate(date : Pair<String, String>) {
        _selectedDate.value = date
    }

    fun setDateVisibility() {
        _isDateVisible.value = !(_isDateVisible.value)
    }

    private fun fetchMovieDetail() {
        viewModelScope.launch {
            repository.getMovieDetails(movieId = movieId).collect {
                _movieDetail.value = it
            }
        }
    }

    private fun fetchRecommendations() {
        viewModelScope.launch {
            repository.getRecommendedMovies(movieId = movieId).collect {
                _recommendations.value = it
            }
        }
    }

    private fun getCredits() {
        viewModelScope.launch {
            repository.getMovieCasts(movieId = movieId).collect {
                _credits.value = it
            }
        }
    }

    private fun getComments() {
        viewModelScope.launch {
            repository.getReviews(movieId = movieId).collect {
                _reviews.value = it
            }
        }
    }

    private fun getYoutubeUrl() {
        viewModelScope.launch {
            repository.getYoutubeUrl(movieId = movieId).collect {
                _youtubeUrls.value = it
            }
        }
    }

    private fun sortByDistance() {
        _movieShowTime.value = _movieShowTime.value.sortedBy { it.distance }
    }

    private fun unSortShowTime() {
        _movieShowTime.value = initialMovieList
    }

    fun setSortedList() {
        _isSortedByDistance.value = !(_isSortedByDistance.value)
        if (_isSortedByDistance.value) sortByDistance() else unSortShowTime()
    }

    fun getBookedDetails(time : String?, theatre : Theatre?) : BookingDetail? {
        return BookingDetail(
            filmName = _movieDetail.value?.data?.title,
            selectedDate = _selectedDate.value,
            selectedTime = time,
            filmTheatreName = theatre?.name,
            availableTimeSlotList = theatre?.showTime
        )
    }
}
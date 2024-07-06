package com.example.movieapp.ui.screens.detailScreen

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.movieapp.data.response.Cast
import com.example.movieapp.data.response.Comment
import com.example.movieapp.data.response.CommonPagingResponse
import com.example.movieapp.data.response.MovieDetail
import com.example.movieapp.data.response.Movie
import com.example.movieapp.data.response.Resource
import com.example.movieapp.data.response.Video
import com.example.movieapp.internal.enums.DetailScreenTabs
import com.example.movieapp.navigation.Screen
import com.example.movieapp.repository.Repository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailViewModel @Inject constructor(private val repository : Repository,
    savedStateHandle : SavedStateHandle
    ) : ViewModel() {

    private val _selectedTab = MutableStateFlow(value = DetailScreenTabs.Booking)
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

    private val _isChecked = MutableStateFlow(value = false)
    val isChecked : Flow<Boolean> = _isChecked

    val tabList = listOf(DetailScreenTabs.About, DetailScreenTabs.Booking)

    fun setTab(tab : DetailScreenTabs) {
        if (_selectedTab.value == tab) return
        _selectedTab.value = tab
    }

    init {
//        fetchMovieDetail()
//        fetchRecommendations()
//        getCredits()
//        getComments()
//        getYoutubeUrl()
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

    fun setSwitchBoxValue() {
        _isChecked.value = !_isChecked.value
    }
}
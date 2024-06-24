package com.example.movieapp.ui.screens.homeScreen

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.movieapp.data.response.CommonResponse
import com.example.movieapp.data.response.NowPlaying
import com.example.movieapp.data.response.Resource
import com.example.movieapp.repository.Repository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(private val repository: Repository) : ViewModel() {

//    private val _movieList = MutableLiveData<Resource<List<NowPlaying>>>()
//    val movieList : LiveData<Resource<List<NowPlaying>>> = _movieList

    private val _movieList = MutableStateFlow<Resource<CommonResponse<List<NowPlaying>>>?>(value = null)
    val movieList : Flow<Resource<CommonResponse<List<NowPlaying>>>?> = _movieList

    init {
        fetchMovieList()
    }

//    private fun fetchMovieList() {
//        viewModelScope.launch {
//            try {
//                _movieList.postValue(Resource.Loading())
//                val response = repository.getNowPlayingMovies()
//                delay(timeMillis = 900)
//                _movieList.postValue(Resource.Success(response.results))
//            } catch (error : Throwable) {
//                _movieList.postValue(Resource.Error(error = error))
//            }
//        }
//    }

    private fun fetchMovieList() {
        viewModelScope.launch(Dispatchers.IO) {
            repository.getNowPlayingMovies().collect {
                _movieList.value = it
            }
        }
    }
}
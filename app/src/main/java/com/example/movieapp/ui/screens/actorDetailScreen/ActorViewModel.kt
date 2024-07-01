package com.example.movieapp.ui.screens.actorDetailScreen

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.movieapp.data.response.ActorDetail
import com.example.movieapp.data.response.CommonPagingResponse
import com.example.movieapp.data.response.Movie
import com.example.movieapp.data.response.Resource
import com.example.movieapp.navigation.Screen
import com.example.movieapp.repository.Repository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ActorViewModel @Inject constructor(private val repository: Repository, savedStateHandle : SavedStateHandle) : ViewModel() {

    private val actorId = savedStateHandle.get<String?>(Screen.ACTOR_ID)
//    private val actorId = "56322"

    private val _actorDetail = MutableStateFlow<Resource<ActorDetail>?>(value = null)
    val actorDetail : Flow<Resource<ActorDetail>?> = _actorDetail

    private val _actorMovies = MutableStateFlow<Resource<CommonPagingResponse<Movie>>?>(value = null)
    val actorMovies : Flow<Resource<CommonPagingResponse<Movie>>?> = _actorMovies

    private val _isLargeImageVisible = MutableStateFlow(value = false)
    val isLargeImageVisible : Flow<Boolean> = _isLargeImageVisible

    init {
        getActorDetail()
        getActorMovies()
    }

    private fun getActorDetail() {
        viewModelScope.launch {
            repository.getActorDetail(actorId = actorId).collect {
                _actorDetail.value = it
            }
        }
    }

    private fun getActorMovies() {
        viewModelScope.launch {
            repository.getActorMovies(actorId = actorId).collect {
                _actorMovies.value = it
            }
        }
    }

    fun setImageVisibility() {
        _isLargeImageVisible.value = !_isLargeImageVisible.value
    }
}
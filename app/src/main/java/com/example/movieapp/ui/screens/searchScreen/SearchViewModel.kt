package com.example.movieapp.ui.screens.searchScreen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import com.example.movieapp.repository.Repository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(repository : Repository) : ViewModel() {

    private val _searchKey = MutableStateFlow<String?>(value = null)
    val searchKey : Flow<String?> = _searchKey

    fun setSearchKey(value : String?) {
        _searchKey.value = value
    }

    @OptIn(ExperimentalCoroutinesApi::class, FlowPreview::class)
    val searchMovies = _searchKey.debounce(timeoutMillis = 1000).flatMapLatest {
        repository.searchMovies(searchKey = it)
    }.cachedIn(scope = viewModelScope)
}
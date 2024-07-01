package com.example.movieapp.ui.screens.searchScreen

import com.example.movieapp.data.api.APIInterface
import com.example.movieapp.data.response.CommonPagingResponse
import com.example.movieapp.data.response.Movie
import com.example.movieapp.paging.BasePagingSource
import javax.inject.Inject

class SearchPagingSource @Inject constructor(private val apiInterface : APIInterface,
                                             private val searchKey : String?) : BasePagingSource<Movie>() {

    override suspend fun load(page: Int, limit: Int): CommonPagingResponse<Movie> {
        return apiInterface.searchMovie(page = page, searchKey = searchKey)
    }

}
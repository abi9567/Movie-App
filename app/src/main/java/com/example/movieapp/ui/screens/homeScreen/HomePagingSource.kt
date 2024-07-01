package com.example.movieapp.ui.screens.homeScreen

import com.example.movieapp.data.api.APIInterface
import com.example.movieapp.data.response.CommonPagingResponse
import com.example.movieapp.data.response.Movie
import com.example.movieapp.paging.BasePagingSource
import kotlinx.coroutines.delay
import javax.inject.Inject

class HomePagingSource @Inject constructor(private val apiInterface : APIInterface,
                                           private val releaseDate : String?)
    : BasePagingSource<Movie>() {

    override suspend fun load(page: Int, limit: Int) : CommonPagingResponse<Movie> {
        if (page == 1) { delay(timeMillis = 900) }
        return apiInterface.nowPlayingMovieList(page = page,
            releaseDate = releaseDate
        )
    }
}
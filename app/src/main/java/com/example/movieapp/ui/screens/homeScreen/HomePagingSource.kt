package com.example.movieapp.ui.screens.homeScreen

import com.example.movieapp.data.api.APIInterface
import com.example.movieapp.data.response.CommonPagingResponse
import com.example.movieapp.data.response.NowPlaying
import com.example.movieapp.paging.BasePagingSource
import javax.inject.Inject

class HomePagingSource @Inject constructor(private val apiInterface : APIInterface)
    : BasePagingSource<NowPlaying>() {

    override suspend fun load(page: Int, limit: Int) : CommonPagingResponse<NowPlaying> {
        return apiInterface.nowPlayingMovieList(page = page)
    }
}
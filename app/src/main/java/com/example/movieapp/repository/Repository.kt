package com.example.movieapp.repository

import com.example.movieapp.data.api.APIInterface
import com.example.movieapp.data.response.CommonResponse
import com.example.movieapp.data.response.NowPlaying
import com.example.movieapp.data.response.Resource
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class Repository @Inject constructor(private val apiInterface : APIInterface) {

    suspend fun getNowPlayingMovies() : Flow<Resource<CommonResponse<List<NowPlaying>>>> {
        return flow {
            emit(value = Resource.Loading())
            try {
                val response = apiInterface.nowPlayingMovieList()
                emit(value = Resource.Success(data = response))
            } catch (error : Throwable) {
                emit(value = Resource.Error(error = error))
            }
        }
    }
}
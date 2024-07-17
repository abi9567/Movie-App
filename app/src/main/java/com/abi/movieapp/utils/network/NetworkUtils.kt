package com.abi.movieapp.utils.network

import com.abi.movieapp.data.response.Resource
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.flow

object NetworkUtils {
    suspend fun <T> fetchData(
        apiRequest : suspend () -> T
    ) = flow {
        try {
            emit(value = Resource.Loading())
            val response = apiRequest()
            delay(timeMillis = 900)
            emit(value = Resource.Success(data = response))
        } catch (error: Throwable) {
            emit(value = Resource.Error(error = error))
        }
    }
}
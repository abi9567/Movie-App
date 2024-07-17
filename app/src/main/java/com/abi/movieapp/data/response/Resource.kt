package com.abi.movieapp.data.response

sealed class Resource<T>(
    var data : T? = null,
    val error : Throwable? = null
) {
    class Loading<T> : Resource<T>()
    class Success<T>(data: T?) : Resource<T>(data)
    class Error<T>(error: Throwable?) : Resource<T>(error = error)
}
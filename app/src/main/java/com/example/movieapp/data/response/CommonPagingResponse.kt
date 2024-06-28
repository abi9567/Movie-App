package com.example.movieapp.data.response

data class CommonPagingResponse<T>(
    val results : List<T>?
)

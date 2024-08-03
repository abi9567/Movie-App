package com.abi.movieapp.data.response

import com.google.gson.annotations.SerializedName

data class CommonPagingResponse<T>(
    val results : List<T>?,

    @SerializedName("page")
    val currentPageCount : Int?
)

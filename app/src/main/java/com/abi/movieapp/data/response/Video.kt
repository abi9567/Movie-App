package com.abi.movieapp.data.response

import com.google.gson.annotations.SerializedName

data class Video(

    @SerializedName("key")
    val youtubeKey : String?,

    @SerializedName("site")
    val site : String?
)

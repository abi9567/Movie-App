package com.example.movieapp.data.response

import com.google.gson.annotations.SerializedName

data class NowPlaying(

    @SerializedName("title")
    val title : String?,

    @SerializedName("vote_average")
    val rating : Double?,

    @SerializedName("poster_path")
    val imageUrl : String?
)

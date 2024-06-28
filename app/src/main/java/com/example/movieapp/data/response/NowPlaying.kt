package com.example.movieapp.data.response

import com.google.gson.annotations.SerializedName

data class NowPlaying(

    @SerializedName("id")
    val id : Int?,

    @SerializedName("title")
    val title : String?,

    @SerializedName("vote_average")
    val rating : Double?,

    @SerializedName("poster_path")
    val imageUrl : String?
) {
    val isLessRating : Boolean
        get() = ((rating ?: 0.0) < 6.0)
}

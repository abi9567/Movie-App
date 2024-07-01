package com.example.movieapp.data.response

import com.google.gson.annotations.SerializedName

data class Movie(

    @SerializedName("id")
    val id : Int?,

    @SerializedName("title")
    val title : String?,

    @SerializedName("genre_ids")
    val genreIds : List<Int?>?,

    @SerializedName("vote_average")
    val rating : Double?,

    @SerializedName("poster_path")
    val imageUrl : String?
) {
    val isLessRating : Boolean
        get() = ((rating ?: 0.0) < 6.0)

    val genreName : String?
        get() = Genre.genreListWithName.find { list ->
            genreIds?.contains(element = list.id) ?: false
    }?.name
}

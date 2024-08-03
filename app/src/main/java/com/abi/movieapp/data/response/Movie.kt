package com.abi.movieapp.data.response

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.abi.movieapp.config.AppConfig
import com.google.gson.annotations.SerializedName

@Entity(tableName = AppConfig.MOVIE_DB_NAME)
data class Movie(

    @PrimaryKey
    @SerializedName("id")
    val id : Int?,

    @SerializedName("title")
    val title : String?,

    @SerializedName("release_date")
    val releaseDate : String?,

//    @SerializedName("genre_ids")
//    val genreIds : List<Int?>?,

    @SerializedName("vote_average")
    val rating : Double?,

    @SerializedName("original_language")
    val movieLanguage : String?,

    @SerializedName("poster_path")
    val imageUrl : String?
) {
    val isLessRating : Boolean
        get() = ((rating ?: 0.0) < 6.0)

//    val genreName : String?
//        get() = Genre.genreListWithName.find { list ->
//            genreIds?.contains(element = list.id) ?: false
//    }?.name

    val genreName : String?
        get() = "Abi"
}

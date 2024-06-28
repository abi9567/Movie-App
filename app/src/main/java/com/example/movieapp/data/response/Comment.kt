package com.example.movieapp.data.response

import com.google.gson.annotations.SerializedName
import java.util.Date

data class Comment(

    @SerializedName("author")
    val author : String?,

    @SerializedName("author_details")
    val authorDetails : AuthorDetail?,

    @SerializedName("content")
    val comment : String?,

    @SerializedName("created_at")
    val date : Date?

)

data class AuthorDetail(
    @SerializedName("name")
    val name : String?,

    @SerializedName("username")
    val userName : String?,

    @SerializedName("avatar_path")
    val profileImage : String?,

    @SerializedName("rating")
    val rating : Double?
)

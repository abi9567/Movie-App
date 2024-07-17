package com.abi.movieapp.data.response

import com.google.gson.annotations.SerializedName

data class Person(

    val id : Int?,
    val name : String?,

    @SerializedName("profile_path")
    val profilePicture : String?,

    @SerializedName("character")
    val characterName : String?,

    @SerializedName("department")
    val department : String?
)

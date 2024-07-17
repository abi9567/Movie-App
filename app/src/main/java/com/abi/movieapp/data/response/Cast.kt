package com.abi.movieapp.data.response

import com.google.gson.annotations.SerializedName

data class Cast(
    @SerializedName("cast")
    val cast : List<Person>?,

    @SerializedName("crew")
    val crew : List<Person>?
)
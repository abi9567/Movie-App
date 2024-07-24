package com.abi.movieapp.data.response

import com.google.gson.annotations.SerializedName

data class AvailableLanguage(
    val name : String?,

    @SerializedName("english_name")
    val englishName : String?
)

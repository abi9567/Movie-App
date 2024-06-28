package com.example.movieapp.data.response

import com.google.gson.annotations.SerializedName

data class ProductionCompanies(

    @SerializedName("logo_path")
    val logo : String?,

    @SerializedName("name")
    val name : String?,

    @SerializedName("origin_country")
    val country : String?
)

package com.abi.movieapp.data.response

import com.google.gson.annotations.SerializedName

data class MovieDetail(

    @SerializedName("backdrop_path")
    val backgroundImage : String?,

    @SerializedName("genres")
    val genres : List<Genre>?,

    @SerializedName("adult")
    val adult : Boolean?,

    @SerializedName("spoken_languages")
    val availableLanguages : List<AvailableLanguage>?,

    @SerializedName("budget")
    val budget : Double?,

    @SerializedName("original_title")
    val title : String?,

    @SerializedName("overview")
    val description : String?,

    @SerializedName("production_companies")
    val productionCompanies : List<ProductionCompanies>?,

    @SerializedName("release_date")
    val releaseDate : String?,

    @SerializedName("runtime")
    val durationInMinutes : Int?,

    @SerializedName("vote_average")
    val rating : Double?,

    @SerializedName("vote_count")
    val totalVotes : Int?
) {
    val genreWithComma : String?
        get() = genres?.joinToString {
            it.name ?: ""
        }
}

package com.example.movieapp.data.response

import com.google.gson.annotations.SerializedName

data class ActorDetail(

    @SerializedName("name")
    val name : String?,

    @SerializedName("biography")
    val biography : String?,

    @SerializedName("birthday")
    val dateOfBirth : String?,

    @SerializedName("deathday")
    val deathDay : String?,

    @SerializedName("gender")
    val gender : Int?,

    @SerializedName("place_of_birth")
    val placeOfBirth : String?,

    @SerializedName("popularity")
    val popularity : Double?,

    @SerializedName("profile_path")
    val profileImage : String?,

    @SerializedName("also_known_as")
    val otherNames : List<String?>?,

    @SerializedName("known_for_department")
    val knownFor : String?

)

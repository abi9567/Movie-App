package com.example.movieapp.data.api

import com.example.movieapp.data.response.CommonResponse
import com.example.movieapp.data.response.NowPlaying
import retrofit2.http.GET
import retrofit2.http.Query

interface APIInterface {

    @GET("movie/now_playing")
    suspend fun nowPlayingMovieList(
        @Query("page") page: Int = 1,
        @Query("with_genres") genreId: String? = null
    ) : CommonResponse<List<NowPlaying>>

}
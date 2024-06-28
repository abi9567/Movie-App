package com.example.movieapp.data.api

import com.example.movieapp.data.response.Cast
import com.example.movieapp.data.response.Comment
import com.example.movieapp.data.response.CommonPagingResponse
import com.example.movieapp.data.response.MovieDetail
import com.example.movieapp.data.response.NowPlaying
import com.example.movieapp.data.response.Video
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface APIInterface {

    @GET("movie/upcoming")
    suspend fun nowPlayingMovieList(
        @Query("page") page: Int,
        @Query("with_genres") genreId: String? = null
    ) : CommonPagingResponse<NowPlaying>

    @GET("movie/{movie_id}")
    suspend fun getMovieDetails(
        @Path("movie_id") movieId : String?
    ) : MovieDetail

    @GET("movie/{movie_id}/recommendations")
    suspend fun getRecommendations(
        @Path("movie_id") movieId : String?
    ) : CommonPagingResponse<NowPlaying>

    @GET("movie/{movie_id}/credits")
    suspend fun getCredits(
        @Path("movie_id") movieId: String?
    ) : Cast

    @GET("movie/{movie_id}/reviews")
    suspend fun getReviews(
        @Path("movie_id") movieId: String?
    ) : CommonPagingResponse<Comment>

    @GET("movie/{movie_id}/videos")
    suspend fun getYoutubeVideoUrl(
        @Path("movie_id") movieId : String?
    ) : CommonPagingResponse<Video>

}
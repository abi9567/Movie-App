package com.example.movieapp.data.api

import com.example.movieapp.data.response.ActorDetail
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

    @GET("discover/movie")
    suspend fun nowPlayingMovieList(
        @Query("page") page: Int,
        @Query("include_adult") includeAdult : Boolean = true,
        @Query("include_video") includeVideo : Boolean = true,
        @Query("with_original_language") language : String = "ml",
        @Query("region") region : String = "IN",
        @Query("release_date.lte") releaseDate : String?,
        @Query("sort_by") sortBy : String = "primary_release_date.desc"
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

    @GET("person/{person_id}")
    suspend fun getActorDetail(
        @Path("person_id") actorId : String?
    ) : ActorDetail

    @GET("discover/movie")
    suspend fun getActorFilms(
        @Query("page") page: Int = 1,
        @Query("with_people") actorId : String?
    ) : CommonPagingResponse<NowPlaying>

}
package com.abi.movieapp.data.api

import com.abi.movieapp.data.response.ActorDetail
import com.abi.movieapp.data.response.Cast
import com.abi.movieapp.data.response.Comment
import com.abi.movieapp.data.response.CommonPagingResponse
import com.abi.movieapp.data.response.MovieDetail
import com.abi.movieapp.data.response.Movie
import com.abi.movieapp.data.response.Video
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface APIInterface {

    @GET("discover/movie")
    suspend fun nowPlayingMovieList(
        @Query("page") page: Int,
        @Query("with_original_language") language : String = "ml",
        @Query("region") region : String = "IN",
        @Query("release_date.lte") releaseDate : String?,
        @Query("sort_by") sortBy : String = "primary_release_date.desc"
    ) : CommonPagingResponse<Movie>

    @GET("movie/{movie_id}")
    suspend fun getMovieDetails(
        @Path("movie_id") movieId : String?
    ) : MovieDetail

    @GET("movie/{movie_id}/recommendations")
    suspend fun getRecommendations(
        @Path("movie_id") movieId : String?
    ) : CommonPagingResponse<Movie>

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
    ) : CommonPagingResponse<Movie>

    @GET("search/movie")
    suspend fun searchMovie(
        @Query("page") page : Int?,
        @Query("query") searchKey : String?
    ) : CommonPagingResponse<Movie>

}
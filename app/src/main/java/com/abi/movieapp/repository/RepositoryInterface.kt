package com.abi.movieapp.repository

import androidx.paging.PagingData
import com.abi.movieapp.data.response.ActorDetail
import com.abi.movieapp.data.response.Cast
import com.abi.movieapp.data.response.Comment
import com.abi.movieapp.data.response.CommonPagingResponse
import com.abi.movieapp.data.response.MovieDetail
import com.abi.movieapp.data.response.Movie
import com.abi.movieapp.data.response.Resource
import com.abi.movieapp.data.response.Video
import kotlinx.coroutines.flow.Flow

interface RepositoryInterface {
    fun getNowPlayingMovies(releaseDate : String?, language : String?) : Flow<PagingData<Movie>>
    suspend fun getMovieDetails(movieId : String?) : Flow<Resource<MovieDetail>>
    suspend fun getRecommendedMovies(movieId: String?) : Flow<Resource<CommonPagingResponse<Movie>>>
    suspend fun getMovieCasts(movieId: String?) : Flow<Resource<Cast>>
    suspend fun getReviews(movieId: String?) : Flow<Resource<CommonPagingResponse<Comment>>>
    suspend fun getYoutubeUrl(movieId: String?) : Flow<Resource<CommonPagingResponse<Video>>>
    suspend fun getActorDetail(actorId : String?) : Flow<Resource<ActorDetail>>
    suspend fun getActorMovies(actorId: String?) : Flow<Resource<CommonPagingResponse<Movie>>>
    suspend fun searchMovies(searchKey : String?) : Flow<PagingData<Movie>>
}
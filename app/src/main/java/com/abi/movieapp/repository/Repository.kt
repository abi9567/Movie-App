package com.abi.movieapp.repository

import android.util.Log
import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.abi.movieapp.data.api.APIInterface
import com.abi.movieapp.data.response.ActorDetail
import com.abi.movieapp.data.response.Cast
import com.abi.movieapp.data.response.Comment
import com.abi.movieapp.data.response.CommonPagingResponse
import com.abi.movieapp.data.response.MovieDetail
import com.abi.movieapp.data.response.Movie
import com.abi.movieapp.data.response.Resource
import com.abi.movieapp.data.response.Video
import com.abi.movieapp.local.MovieDataBase
import com.abi.movieapp.local.RemoteMediator
import com.abi.movieapp.ui.screens.searchScreen.SearchPagingSource
import com.abi.movieapp.utils.network.NetworkUtils
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class Repository @Inject constructor(private val apiInterface : APIInterface,
                                     private val movieDataBase : MovieDataBase) : RepositoryInterface {

//    override fun getNowPlayingMovies(releaseDate : String?, language : String?) : Flow<PagingData<Movie>> =
//        Pager(
//            pagingSourceFactory = {
//                HomePagingSource(apiInterface = apiInterface,
//                    language = language,
//                    releaseDate = releaseDate)
//            },
//            config = PagingConfig(pageSize = 10)
//        ).flow

    @OptIn(ExperimentalPagingApi::class)
    override fun getNowPlayingMovies(releaseDate : String?, language : String?) : Flow<PagingData<Movie>> =
        Pager(
            remoteMediator = RemoteMediator(apiInterface = apiInterface,
                releaseDate = releaseDate, language = language,
                movieDataBase = movieDataBase),
            pagingSourceFactory = { movieDataBase.movieDao().getMovieList()},
            config = PagingConfig(pageSize = 10)
        ).flow

    override suspend fun getMovieDetails(movieId : String?) : Flow<Resource<MovieDetail>> {
        return NetworkUtils.fetchData {
            apiInterface.getMovieDetails(movieId = movieId)
        }
    }

    override suspend fun getRecommendedMovies(movieId: String?): Flow<Resource<CommonPagingResponse<Movie>>> {
        return NetworkUtils.fetchData {
            apiInterface.getRecommendations(movieId = movieId)
        }
    }

    override suspend fun getMovieCasts(movieId: String?): Flow<Resource<Cast>> {
        return NetworkUtils.fetchData {
            apiInterface.getCredits(movieId = movieId)
        }
    }

    override suspend fun getReviews(movieId: String?): Flow<Resource<CommonPagingResponse<Comment>>> {
        return NetworkUtils.fetchData {
            apiInterface.getReviews(movieId = movieId)
        }
    }

    override suspend fun getYoutubeUrl(movieId: String?): Flow<Resource<CommonPagingResponse<Video>>> {
        return NetworkUtils.fetchData {
            apiInterface.getYoutubeVideoUrl(movieId = movieId)
        }
    }

    override suspend fun getActorDetail(actorId: String?): Flow<Resource<ActorDetail>> {
        return NetworkUtils.fetchData {
            apiInterface.getActorDetail(actorId = actorId)
        }
    }

    override suspend fun getActorMovies(actorId: String?): Flow<Resource<CommonPagingResponse<Movie>>> {
        return NetworkUtils.fetchData {
            apiInterface.getActorFilms(actorId = actorId)
        }
    }

    override suspend fun searchMovies(searchKey: String?): Flow<PagingData<Movie>> {
        return Pager(
            pagingSourceFactory = { SearchPagingSource(apiInterface = apiInterface, searchKey = searchKey) },
            config = PagingConfig(pageSize = 10),
        ).flow
    }
}
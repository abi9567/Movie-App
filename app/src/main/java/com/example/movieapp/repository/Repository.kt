package com.example.movieapp.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.example.movieapp.data.api.APIInterface
import com.example.movieapp.data.response.Cast
import com.example.movieapp.data.response.Comment
import com.example.movieapp.data.response.CommonPagingResponse
import com.example.movieapp.data.response.MovieDetail
import com.example.movieapp.data.response.NowPlaying
import com.example.movieapp.data.response.Resource
import com.example.movieapp.data.response.Video
import com.example.movieapp.ui.screens.homeScreen.HomePagingSource
import com.example.movieapp.utils.network.NetworkUtils
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class Repository @Inject constructor(private val apiInterface : APIInterface) : RepositoryInterface {

    override fun getNowPlayingMovies() : Flow<PagingData<NowPlaying>> =
        Pager(
            pagingSourceFactory = {
                HomePagingSource(apiInterface = apiInterface)
            },
            config = PagingConfig(pageSize = 10)
        ).flow

    override suspend fun getMovieDetails(movieId : String?) : Flow<Resource<MovieDetail>> {
        return NetworkUtils.fetchData {
            apiInterface.getMovieDetails(movieId = movieId)
        }
    }

    override suspend fun getRecommendedMovies(movieId: String?): Flow<Resource<CommonPagingResponse<NowPlaying>>> {
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
}
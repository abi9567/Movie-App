package com.abi.movieapp.local

import android.util.Log
import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import androidx.room.withTransaction
import com.abi.movieapp.data.api.APIInterface
import com.abi.movieapp.data.response.Movie

@OptIn(ExperimentalPagingApi::class)
class RemoteMediator (private val apiInterface : APIInterface,
                      val language : String?, val releaseDate : String?,
                      private val movieDataBase : MovieDataBase) : RemoteMediator<Int, Movie>() {

    override suspend fun initialize() : InitializeAction {
        return InitializeAction.LAUNCH_INITIAL_REFRESH
    }

    override suspend fun load(loadType: LoadType, state: PagingState<Int, Movie>): MediatorResult {
            val page = when(loadType) {
                LoadType.REFRESH -> 1
                LoadType.PREPEND -> return MediatorResult.Success(endOfPaginationReached = true)
                LoadType.APPEND -> {
                    val lastItem = state.lastItemOrNull()
                    val lastItemIndex = state.pages.lastOrNull()?.lastIndexOf(element = lastItem)
                    Log.d("RemoteMediator", "LastItem -> " + lastItem.toString())
                    Log.d("RemoteMediator", "LastItemIndex -> " + lastItem.toString())

                    if (lastItemIndex == null) return MediatorResult.Success(endOfPaginationReached = true)
                    else (lastItemIndex / state.config.pageSize) + 1
                }
            }

        return try {
            val networkResponse = apiInterface.nowPlayingMovieList(
                page = page,
                language = language,
                releaseDate = releaseDate
            ).results

            networkResponse?.let {
                movieDataBase.withTransaction {
                if (loadType == LoadType.REFRESH) {
                    movieDataBase.movieDao().clearMovies()
                }
                    movieDataBase.movieDao().insertMovies(movies = networkResponse)
                }
            }

            MediatorResult.Success(endOfPaginationReached = networkResponse.isNullOrEmpty())

        } catch (error : Exception) {
            Log.d("RemoteMediator", error.message.toString())
            return MediatorResult.Error(throwable = error)
        }
    }
}
package com.example.movieapp.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.movieapp.data.response.CommonPagingResponse
import retrofit2.HttpException
import java.io.IOException

private const val STARTING_PAGE_INDEX = 1

abstract class BasePagingSource<T : Any> : PagingSource<Int, T>() {

    override fun getRefreshKey(state: PagingState<Int, T>): Int? {
        return state.anchorPosition
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, T> {
        try {
            val position = params.key ?: STARTING_PAGE_INDEX
            val response = load(page = position, limit = params.loadSize)
            val hasNextPage = response.results?.isNotEmpty() == true

            return LoadResult.Page(
                data = response.results ?: emptyList(),
                nextKey = if (hasNextPage) position + 1 else null,
                prevKey = if (position == STARTING_PAGE_INDEX) null else position - 1
            )

        } catch (exception: IOException) {
            return LoadResult.Error(exception)
        } catch (exception: HttpException) {
            return LoadResult.Error(exception)
        }
    }
    abstract suspend fun load(page: Int, limit: Int) : CommonPagingResponse<T>
}
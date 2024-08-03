package com.abi.movieapp.local

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.abi.movieapp.config.AppConfig
import com.abi.movieapp.data.response.Movie

@Dao
interface MovieDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMovies(movies : List<Movie>)

    @Query("SELECT * FROM ${AppConfig.MOVIE_DB_NAME}")
    fun getMovieList() : PagingSource<Int, Movie>

    @Query("DELETE FROM ${AppConfig.MOVIE_DB_NAME}")
    suspend fun clearMovies()
}
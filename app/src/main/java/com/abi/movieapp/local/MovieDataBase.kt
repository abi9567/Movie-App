package com.abi.movieapp.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.abi.movieapp.data.response.Movie

@Database(entities = [Movie::class], version = 1)
abstract class MovieDataBase : RoomDatabase() {
    abstract fun movieDao() : MovieDao
}
package com.abi.movieapp.di

import android.content.Context
import androidx.room.Room
import com.abi.movieapp.config.AppConfig
import com.abi.movieapp.data.api.APIInterface
import com.abi.movieapp.local.MovieDataBase
import com.abi.movieapp.repository.Repository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {

    @Singleton
    @Provides
    fun provideDataBase(@ApplicationContext context: Context) : MovieDataBase {
        return Room.databaseBuilder(
            context = context,
            MovieDataBase::class.java,
            AppConfig.MOVIE_DB_NAME
        ).build()
    }

    @Singleton
    @Provides
    fun provideRepository(
        apiInterface: APIInterface,
        movieDataBase: MovieDataBase
    ) : Repository  {
        return Repository(apiInterface = apiInterface,
            movieDataBase = movieDataBase)
    }
}
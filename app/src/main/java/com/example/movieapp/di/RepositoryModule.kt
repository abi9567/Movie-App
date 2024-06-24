package com.example.movieapp.di

import com.example.movieapp.data.api.APIInterface
import com.example.movieapp.repository.Repository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {

    @Singleton
    @Provides
    fun provideRepository(
        apiInterface: APIInterface
    ) : Repository  {
        return Repository(apiInterface = apiInterface)
    }
}
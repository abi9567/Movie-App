package com.abi.movieapp.di

import android.content.Context
import com.abi.movieapp.utils.other.DataStoreUtil
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DataStoreModule {

    @Provides
    @Singleton
    fun dataStoreUtil(@ApplicationContext context: Context) : DataStoreUtil {
        return DataStoreUtil(context = context)
    }
}
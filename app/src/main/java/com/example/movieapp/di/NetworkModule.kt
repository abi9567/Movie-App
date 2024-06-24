package com.example.movieapp.di

import com.example.movieapp.BuildConfig
import com.example.movieapp.config.AppConfig
import com.example.movieapp.data.api.APIInterface
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.google.gson.internal.bind.DateTypeAdapter
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.Date
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Singleton
    @Provides
    fun provideClient() : OkHttpClient {
        val client = OkHttpClient().newBuilder()
            .connectTimeout(60, TimeUnit.SECONDS)
            .readTimeout(60, TimeUnit.SECONDS)
            .writeTimeout(60, TimeUnit.SECONDS)

        if(BuildConfig.DEBUG) {
            val interceptor = HttpLoggingInterceptor()
            interceptor.level = HttpLoggingInterceptor.Level.BODY
            client.addInterceptor(interceptor = interceptor)
        }

        client.addInterceptor { chain ->
            val authorizationHeader = chain.request().header(name = "Authorization")
            val builder = chain.request().newBuilder()
                .addHeader(name = "Accept", value = "application/json")
            if (authorizationHeader.isNullOrEmpty()) {
                builder.addHeader(name = "Authorization", value = "Bearer ${BuildConfig.API_KEY}")
            }
            return@addInterceptor chain.proceed(request = builder.build())
        }

        client.addInterceptor { chain ->
            val originalRequest = chain.request()
            val originalUrl = originalRequest.url

            val newUrl = originalUrl.newBuilder()
                .addQueryParameter(name = "api_key", value = BuildConfig.API_KEY)
                .build()

            val newRequest = originalRequest.newBuilder()
                .url(url = newUrl)
                .build()
            return@addInterceptor chain.proceed(request = newRequest)
        }

        return client.build()
    }

    @Singleton
    @Provides
    fun provideGson() : Gson {
        return GsonBuilder()
            .registerTypeAdapter(Date::class.java, DateTypeAdapter())
            .setLenient()
            .create()
    }


    @Singleton
    @Provides
    fun provideRetrofitBuilder(
        client: OkHttpClient,
        gson: Gson
    ) : Retrofit {
         return Retrofit.Builder()
             .client(client)
             .addConverterFactory(GsonConverterFactory.create(gson))
             .baseUrl(AppConfig.BASE_URL)
             .build()
    }

    @Singleton
    @Provides
    fun provideApiService(
        retrofit : Retrofit
    ) : APIInterface {
        return retrofit
            .create(APIInterface::class.java)
    }

}
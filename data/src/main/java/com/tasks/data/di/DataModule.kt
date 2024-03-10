package com.tasks.data.di

import com.google.gson.GsonBuilder
import com.tasks.core.network.BaseHeadersInterceptor
import com.tasks.data.BuildConfig
import com.tasks.data.remote.ApiService
import com.tasks.data.remote.adapter.ResultCallAdapterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DataModule {
    @Singleton
    @Provides
    fun provideHttpClient(
    ): OkHttpClient {
        return OkHttpClient.Builder().apply {
            connectTimeout(30, TimeUnit.SECONDS)
            readTimeout(30, TimeUnit.SECONDS)
            writeTimeout(30, TimeUnit.SECONDS)
            addInterceptor(BaseHeadersInterceptor())
            addInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
        }.build()
    }

    @Provides
    fun provideGsonConverterFactory(): GsonConverterFactory {
        val gson = GsonBuilder().create()
        return GsonConverterFactory.create(gson)
    }

    @Provides
    @Singleton
    fun provideApiService(
        gsonConverterFactory: GsonConverterFactory,
        okHttpClient: OkHttpClient
    ): ApiService {
        return Retrofit.Builder()
            .baseUrl(BuildConfig.OPEN_WEATHER_URL)
            .addConverterFactory(gsonConverterFactory)
            .addCallAdapterFactory(ResultCallAdapterFactory())
            .client(okHttpClient)
            .build()
            .create(ApiService::class.java)
    }
}
package com.tasks.data.di

import com.tasks.data.remote.ApiService
import com.tasks.data.repo.WeatherRepositoryImpl
import com.tasks.domain.repo.WeatherRepository
import com.tasks.domain.usecase.GetWeatherUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {

    @Provides
    fun provideCurrentWeatherRepo(apiService: ApiService): WeatherRepository =
        WeatherRepositoryImpl(apiService)

    @Provides
    fun provideUseCase(repo: WeatherRepository): GetWeatherUseCase =
        GetWeatherUseCase(repo)

}
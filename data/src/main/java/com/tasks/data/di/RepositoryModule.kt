package com.tasks.data.di

import com.tasks.data.repo.WeatherRepositoryImpl
import com.tasks.domain.repo.WeatherRepository
import com.tasks.domain.usecase.GetWeatherUseCase
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
interface RepositoryModule {

    @Binds
    fun provideCurrentWeatherRepo(impl: WeatherRepositoryImpl): WeatherRepository

    @Provides
    fun provideUseCase(repo: WeatherRepository): GetWeatherUseCase = GetWeatherUseCase(repo)

}
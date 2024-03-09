package com.tasks.data.repo

import com.tasks.data.remote.ApiService
import com.tasks.domain.model.CurrentWeather
import com.tasks.domain.repo.WeatherRepository

class WeatherRepositoryImpl(private val apiService: ApiService) :
    WeatherRepository {

    override suspend fun getWeather(location: String, days: String): Result<CurrentWeather> =
        apiService.getCurrentWeather(location = location, days = days)

}
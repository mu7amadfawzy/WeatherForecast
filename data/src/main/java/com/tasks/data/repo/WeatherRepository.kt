package com.tasks.data.repo

import com.tasks.data.remote.ApiService
import com.tasks.domain.model.CurrentWeather
import com.tasks.domain.repo.WeatherRepository
import javax.inject.Inject

class WeatherRepositoryImpl @Inject constructor(private val apiService: ApiService) :
    WeatherRepository {

    override suspend fun getWeather(days: String): Result<CurrentWeather> =
        apiService.getCurrentWeather()

}
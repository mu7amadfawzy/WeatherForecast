package com.tasks.domain.repo

import com.tasks.domain.model.CurrentWeather

interface WeatherRepository {
    suspend fun getWeather(location: String, days: String): Result<CurrentWeather>
}
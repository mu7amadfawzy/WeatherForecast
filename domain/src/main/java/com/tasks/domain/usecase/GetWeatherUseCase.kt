package com.tasks.domain.usecase

import com.tasks.domain.model.CurrentWeather
import com.tasks.domain.repo.WeatherRepository

class GetWeatherUseCase(private val repositories: WeatherRepository) {
    suspend operator fun invoke(
        location: String,
        days: String
    ): Result<CurrentWeather> =
        repositories.getWeather(location, days)

}
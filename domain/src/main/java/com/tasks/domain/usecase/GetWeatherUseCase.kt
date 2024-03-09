package com.tasks.domain.usecase

import com.tasks.domain.model.CurrentWeather
import com.tasks.domain.repo.WeatherRepository

class GetWeatherUseCase(private val repositories: WeatherRepository) {
    suspend operator fun invoke(
        days: String = "1",
        location: String = "Cairo"
    ): Result<CurrentWeather> =
        repositories.getWeather(location, days)

}
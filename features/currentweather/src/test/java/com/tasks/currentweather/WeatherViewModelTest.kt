package com.tasks.currentweather

import com.tasks.currentweather.viewmodel.WeatherViewModel
import com.tasks.domain.model.CurrentWeather
import com.tasks.domain.repo.WeatherRepository
import com.tasks.domain.usecase.GetWeatherUseCase
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Test

class WeatherViewModelTest {

    @Test
    fun `parseDateToTime() returns formatted time string`() = runBlocking {
        // Given
        val viewModel = WeatherViewModel(GetWeatherUseCase(FakeWeatherRepository()))

        // When
        val formattedTime = viewModel.parseDateToTime("2023-03-10 10:30")

        // Then
        assertEquals("10:30 AM", formattedTime)
    }

    @Test
    fun `parseDateToTime() returns original string on parsing error`() = runBlocking {
        // Given
        val viewModel = WeatherViewModel(GetWeatherUseCase(FakeWeatherRepository()))

        // When
        val formattedTime = viewModel.parseDateToTime("invalid_date")

        // Then
        assertEquals("invalid_date", formattedTime)
    }
}

// Fake repository for testing
class FakeWeatherRepository : WeatherRepository {
    override suspend fun getWeather(location: String, days: String): Result<CurrentWeather> {
        return Result.success(CurrentWeather())
    }
}
package com.tasks.domain.usecase

import com.tasks.domain.model.CurrentWeather
import com.tasks.domain.repo.WeatherRepository
import kotlinx.coroutines.runBlocking
import org.junit.Test

class GetWeatherUseCaseTest {
    @Test
    fun `invoke() with valid data should return success()`() = runBlocking {
        // Given
        val repo = getSuccessRepo()
        val useCase = GetWeatherUseCase(repo)

        // When
        val result = useCase()

        // Then
        assert(result == Result.success(CurrentWeather()))
    }

    @Test
    fun `invoke() with custom days should call repository getWeather()`() = runBlocking {
        // Given
        val repo = getFailureRepo()
        val useCase = GetWeatherUseCase(repo)

        // When
        val result = useCase()

        // Then
        assert(result == Result.failure<Any>(Throwable()))
    }

    private fun getSuccessRepo() = object : WeatherRepository {
        override suspend fun getWeather(days: String): Result<CurrentWeather> {
            return Result.success(CurrentWeather())
        }
    }

    private fun getFailureRepo() = object : WeatherRepository {
        override suspend fun getWeather(days: String): Result<CurrentWeather> {
            return Result.failure(Throwable())
        }
    }
}
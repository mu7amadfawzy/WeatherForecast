package com.tasks.domain.usecase

import com.tasks.domain.model.Astro
import com.tasks.domain.model.Condition
import com.tasks.domain.model.CurrentWeather
import com.tasks.domain.model.Day
import com.tasks.domain.model.Forecast
import com.tasks.domain.model.Forecastday
import com.tasks.domain.model.Hour
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
        assert(result.isSuccess)
    }

    @Test
    fun `invoke() with custom days should return success() with matched Forecast days`() =
        runBlocking {
        // Given
            val days = 2
            val repo = getSuccessRepo()
        val useCase = GetWeatherUseCase(repo)

        // When
            val result = useCase(days.toString())

        // Then
            assert(result.isSuccess)
            assert(result.getOrNull()?.forecast?.forecastday?.size == days)
        }

    @Test
    fun `invoke() with failure should return isFailure`() = runBlocking {
        // Given
        val repo = getFailureRepo()
        val useCase = GetWeatherUseCase(repo)

        // When
        val result = useCase()

        // Then
        assert(result.isFailure)
    }

    private fun getFailureRepo() = object : WeatherRepository {
        override suspend fun getWeather(days: String): Result<CurrentWeather> {
            return Result.failure(Throwable())
        }
    }
    private fun getSuccessRepo() = object : WeatherRepository {
        override suspend fun getWeather(days: String): Result<CurrentWeather> {
            val list = mutableListOf<Forecastday>()
            repeat(days.toInt()) {
                list.add(getForeCastDay())
            }
            return Result.success(
                CurrentWeather(forecast = Forecast(list))
            )
        }
    }

    private fun getForeCastDay() =
        Forecastday(
            astro = Astro(
                isMoonUp = 1,
                isSunUp = 1,
                moonIllumination = "50%",
                moonPhase = "Waxing Gibbous",
                moonrise = "2023-08-01 20:00",
                moonset = "2023-08-02 06:00",
                sunrise = "2023-08-01 06:00",
                sunset = "2023-08-01 20:00"
            ),
            date = "2023-08-01",
            dateEpoch = 1685784000,
            day = Day(
                avghumidity = 50.0,
                avgtempC = 30.0,
                avgtempF = 86.0,
                avgvisKm = 10.0,
                avgvisMiles = 6.2,
                condition = Condition(code = 1000, icon = "sunny", text = "Clear"),
                dailyChanceOfRain = 0,
                dailyChanceOfSnow = 0,
                dailyWillItRain = 0,
                dailyWillItSnow = 0,
                maxtempC = 35.0,
                maxtempF = 95.0,
                maxwindKph = 20.0,
                maxwindMph = 12.4,
                mintempC = 25.0,
                mintempF = 77.0,
                totalprecipIn = 0.0,
                totalprecipMm = 0.0,
                totalsnowCm = 0.0,
                uv = 8.0
            ),
            hour = listOf(
                Hour(
                    chanceOfRain = 0,
                    chanceOfSnow = 0,
                    cloud = 50,
                    condition = Condition(code = 1000, icon = "sunny", text = "Clear"),
                    dewpointC = 20.0,
                    dewpointF = 68.0,
                    feelslikeC = 25.0,
                    feelslikeF = 77.0,
                    gustKph = 10.0,
                    gustMph = 6.2,
                    heatindexC = 30.0,
                    heatindexF = 86.0,
                    humidity = 50,
                    isDay = 1,
                    precipIn = 0.0,
                    precipMm = 0.0,
                    pressureIn = 1013.0,
                    pressureMb = 1013.0,
                    tempC = 30.0,
                    tempF = 86.0,
                    time = "2023-08-01 00:00",
                    timeEpoch = 1685784000,
                    uv = 8.0,
                    visKm = 10.0,
                    visMiles = 6.2,
                    willItRain = 0,
                    willItSnow = 0,
                    windDegree = 270,
                    windDir = "W",
                    windKph = 15.0,
                    windMph = 9.3,
                    windchillC = 25.0,
                    windchillF = 77.0
                )
            )
        )
}
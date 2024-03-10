package com.tasks.data.remote

import com.tasks.data.BuildConfig
import com.tasks.domain.model.CurrentWeather
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {
    @GET("forecast.json")
    suspend fun getCurrentWeather(
        @Query("key") key: String = BuildConfig.OPEN_WEATHER_KEY,
        @Query("q") location: String,
        @Query("days") days: String,
        @Query("aqi") aqi: String = "no",
        @Query("alerts") alerts: String = "no",
    ): Result<CurrentWeather>

}
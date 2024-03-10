package com.tasks.currentweather.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tasks.core.dateformat.parseDateToTime
import com.tasks.core.network.Resource
import com.tasks.domain.model.CurrentWeather
import com.tasks.domain.usecase.GetWeatherUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject


@HiltViewModel
class WeatherViewModel @Inject constructor(private val currentWeatherUseCase: GetWeatherUseCase) :
    ViewModel() {
    private val _state = MutableStateFlow<Resource<Any>?>(null)
    val state: StateFlow<Resource<Any>?> = _state

    fun getCurrentWeather(city: String, days: String = "1") =
        viewModelScope.launch {
            _state.value = Resource.Loading()

            var result: Result<CurrentWeather>
            withContext(Dispatchers.IO) {
                result = currentWeatherUseCase(city, days)
            }
            result.fold(
                onSuccess = {
                    _state.value = Resource.Success(it)
                },
                onFailure = {
                    _state.value = Resource.Error("Unknown Error", it)
                }
            )
        }

    fun parseDateToTime(time: String) = time.parseDateToTime()
}
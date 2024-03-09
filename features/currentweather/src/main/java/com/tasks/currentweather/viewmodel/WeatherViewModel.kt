package com.tasks.currentweather.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tasks.data.remote.Resource
import com.tasks.domain.usecase.GetWeatherUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import javax.inject.Inject


@HiltViewModel
class WeatherViewModel @Inject constructor(private val currentWeatherUseCase: GetWeatherUseCase) :
    ViewModel() {

    private val _state = MutableStateFlow<Resource<Any>?>(null)
    val state: StateFlow<Resource<Any>?> = _state

    fun getCurrentWeather(location: String = "Cairo", days: String = "1") =
        viewModelScope.launch {
            _state.value = Resource.Loading()

            currentWeatherUseCase(location, days).fold(
                onSuccess = {
                    _state.value = Resource.Success(it)
                },
                onFailure = {
                    _state.value = Resource.Error("Unknown Error", it)
                }
            )
        }

    fun parseDateToTime(time: String): String {
        val inputSDF = SimpleDateFormat("yyyy-MM-dd hh:mm", Locale.getDefault())
        val outputSDF = SimpleDateFormat("hh:mm a", Locale.getDefault())
        val date: Date? = try {
            inputSDF.parse(time)
        } catch (e: ParseException) {
            return time
        }
        return outputSDF.format(date)

    }

}
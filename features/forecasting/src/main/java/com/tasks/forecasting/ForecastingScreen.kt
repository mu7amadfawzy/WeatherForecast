package com.tasks.forecasting

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.BaselineShift
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import com.tasks.currentweather.R
import com.tasks.currentweather.ui.CityTitle
import com.tasks.currentweather.ui.components.ErrorDialog
import com.tasks.currentweather.ui.components.LoadingBubblesScreen
import com.tasks.currentweather.viewmodel.WeatherViewModel
import com.tasks.data.remote.Resource
import com.tasks.domain.model.CurrentWeather
import com.tasks.domain.model.Day

@Composable
fun ForecastScreen(
    city: String,
    viewModel: WeatherViewModel = hiltViewModel()
) {
    LaunchedEffect(city) {
        viewModel.getCurrentWeather(city = city, days = "10")
    }

    val state by viewModel.state.collectAsState()

    var forecastedWeatherState by remember {
        mutableStateOf(CurrentWeather())
    }

    when (val pageState = state) {
        is Resource.Loading ->
            LoadingBubblesScreen()

        is Resource.Error ->
            ErrorDialog(message = pageState.message) {
                viewModel.getCurrentWeather(city, "10")
            }

        is Resource.Success ->
            forecastedWeatherState = pageState.data as CurrentWeather

        null -> Unit
    }

    Box(modifier = Modifier.fillMaxSize()) {
        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Top
        ) {
            item {
                CityTitle(city)
            }
            forecastedWeatherState.forecast?.forecastday?.let {
                items(items = it) { forecastDay ->
                    DaysItem(
                        date = forecastDay.date,
                        days = forecastDay.day,
                        parseDateToTime = viewModel::parseDateToTime
                    )
                }
            }
        }
    }
}


@Composable
fun DaysItem(
    date: String,
    days: Day,
    parseDateToTime: (time: String) -> String?
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(5.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Column(modifier = Modifier.weight(1f)) {
                parseDateToTime(date)?.let {
                    Text(
                        text = it,
                        style = TextStyle(fontSize = 16.sp, color = colorScheme.onBackground)
                    )
                }
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = days.condition.text,
                    style = TextStyle(fontSize = 16.sp, color = colorScheme.onBackground)
                )
            }

            AsyncImage(
                modifier = Modifier.size(50.dp),
                contentDescription = "Weather icon",
                model = "https:${days.condition.icon}",
                placeholder = painterResource(R.drawable.current_weather)
            )

            Spacer(modifier = Modifier.width(16.dp))

            Column {
                Text( //Max
                    text = buildAnnotatedString {
                        withStyle(
                            style = SpanStyle(
                                fontSize = 16.0.sp,
                                fontWeight = FontWeight(700.0.toInt()),
                                fontStyle = FontStyle.Normal,
                            )
                        ) {
                            append(days.maxtempC.toString())
                        }
                        withStyle(
                            style = SpanStyle(
                                baselineShift = BaselineShift.Superscript,
                                fontSize = 8.0.sp,
                                fontWeight = FontWeight(300.0.toInt()),
                                fontStyle = FontStyle.Normal,
                            )
                        ) { // AnnotatedString.Builder
                            append("o")
                        }
                    },
                    style = TextStyle(fontSize = 16.sp, color = colorScheme.onBackground)
                )

                Spacer(modifier = Modifier.height(8.dp))

                Text( //Min
                    buildAnnotatedString {
                        withStyle(
                            style = SpanStyle(
                                fontSize = 16.0.sp,
                                fontWeight = FontWeight(700.0.toInt()),
                                fontStyle = FontStyle.Normal,
                            )
                        ) {
                            append(days.mintempC.toString())
                        }
                        withStyle(
                            style = SpanStyle(
                                baselineShift = BaselineShift.Superscript,
                                fontSize = 8.0.sp,
                                fontWeight = FontWeight(300.0.toInt()),
                                fontStyle = FontStyle.Normal,
                            )
                        ) { // AnnotatedString.Builder
                            append("o")
                        }
                    },
                    style = TextStyle(fontSize = 16.sp, color = colorScheme.onBackground)
                )
            }
        }
    }
}
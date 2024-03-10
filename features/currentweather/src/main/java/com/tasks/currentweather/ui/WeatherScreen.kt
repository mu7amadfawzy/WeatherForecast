package com.tasks.currentweather.ui

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
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.BaselineShift
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import com.tasks.core.network.Resource
import com.tasks.currentweather.R
import com.tasks.currentweather.ui.components.ErrorDialog
import com.tasks.currentweather.ui.components.LoadingBubblesScreen
import com.tasks.currentweather.viewmodel.WeatherViewModel
import com.tasks.domain.model.Current
import com.tasks.domain.model.CurrentWeather
import com.tasks.domain.model.Hour

@Composable
fun WeatherScreen(
    citySearch: String,
    viewModel: WeatherViewModel = hiltViewModel()
) {
    LaunchedEffect(citySearch) {
        viewModel.getCurrentWeather(citySearch)
    }
    val state by viewModel.state.collectAsState()

    var currentWeatherSate by remember {
        mutableStateOf(CurrentWeather())
    }

    when (val pageState = state) {
        is Resource.Loading -> LoadingBubblesScreen()

        is Resource.Success ->
            currentWeatherSate = pageState.data as CurrentWeather

        is Resource.Error, null -> ErrorDialog(message = pageState?.message) {
            viewModel.getCurrentWeather(citySearch)
        }
    }
    WeatherContent(citySearch, currentWeatherSate, viewModel::parseDateToTime)
}

@Composable
private fun WeatherContent(
    citySearch: String?,
    currentWeatherSate: CurrentWeather,
    parseDateToTime: (time: String) -> String?
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Top
        ) {
            CityTitle(citySearch)
            Spacer(modifier = Modifier.height(16.dp))
            HeaderWeather(currentWeatherSate.current)
            currentWeatherSate.forecast?.forecastday?.get(0)?.hour?.let {
                HourlyForecasting(it, parseDateToTime)
            }

        }
    }
}

@Composable
fun CityTitle(citySearch: String?) {
    citySearch?.let {
        Text(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 10.dp),
            text = it,
            textAlign = TextAlign.Center,
            style = TextStyle(fontSize = 28.sp, color = colorScheme.primary)
        )
    }
}

@Composable
private fun HeaderWeather(currentWeather: Current?) {
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        AsyncImage(
            modifier = Modifier.size(160.dp),
            model = "https:${currentWeather?.condition?.icon}",
            placeholder = painterResource(id = R.drawable.current_weather),
            contentScale = ContentScale.Crop,
            contentDescription = "Weather icon",
        )
        Text(
            text = currentWeather?.condition?.text ?: "Loading...",
            style = TextStyle(fontSize = 24.sp, color = colorScheme.onBackground)
        )
        Spacer(Modifier.height(16.dp))
        TemperatureText(currentWeather?.tempC)
        Spacer(Modifier.height(16.dp))
        WeatherDetails(currentWeather)
    }
}

@Composable
private fun WeatherDetails(current: Current?) {
    Row {
        WeatherLabelAndValue(
            title = "Humidity",
            value = "${current?.humidity ?: ".."}%",
        )
        Spacer(modifier = Modifier.width(8.dp))
        WeatherLabelAndValue(
            title = "UV",
            value = (current?.uv ?: "..").toString()
        )
        Spacer(modifier = Modifier.width(8.dp))
        WeatherLabelAndValue(
            title = "Feels Like",
            value = (current?.feelslikeC ?: "...").toString()
        )
    }
}

@Composable
private fun TemperatureText(temp: Double?) {
    Text(
        text = buildAnnotatedString {
            withStyle(
                style = SpanStyle(
                    fontSize = 32.0.sp,
                    fontWeight = FontWeight(700.0.toInt()),
                    fontStyle = FontStyle.Normal,
                )
            ) {
                append("${temp ?: "..."} ")
            }
            withStyle(
                style = SpanStyle(
                    baselineShift = BaselineShift.Superscript,
                    fontSize = 8.0.sp,
                    fontWeight = FontWeight(300.0.toInt()),
                    fontStyle = FontStyle.Normal,
                )
            ) { append(" o") }
            withStyle(
                style = SpanStyle(
                    fontSize = 32.0.sp,
                    fontWeight = FontWeight(300.0.toInt()),
                    fontStyle = FontStyle.Normal,
                )
            ) { append("C") }
        },
        modifier = Modifier,
        style = TextStyle(fontSize = 32.sp, color = colorScheme.onBackground),
    )
}


@Composable
fun WeatherLabelAndValue(
    title: String,
    value: String,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = title,
            style = TextStyle(fontSize = 16.sp, color = colorScheme.onBackground)
        )
        Spacer(modifier = Modifier.height(2.dp))
        Text(
            text = value,
            style = TextStyle(fontSize = 16.sp, color = colorScheme.onBackground)
        )
    }
}


@Composable
fun HourlyForecasting(
    hours: List<Hour>,
    parseDateToTime: (time: String) -> String?
) {
    Column(modifier = Modifier.padding(16.dp)) {
        Spacer(modifier = Modifier.height(48.dp))
        Text(
            text = "Hourly Status",
            style = TextStyle(fontSize = 16.sp, color = colorScheme.onBackground)
        )
        Spacer(modifier = Modifier.height(8.dp))
        LazyRow {
            items(hours, itemContent = {
                HourItem(
                    time = parseDateToTime(it.time) ?: "",
                    icon = "https:${it.condition.icon}",
                    temp = it.tempC.toString()
                )
                Spacer(modifier = Modifier.width(8.dp))
            })
        }
    }

}

@Composable
private fun HourItem(
    time: String,
    icon: String,
    temp: String
) {
    Card {
        Column(
            modifier = Modifier.padding(8.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            HourTemp(temp)
            AsyncImage(
                modifier = Modifier.size(70.dp),
                model = icon,
                placeholder = painterResource(R.drawable.current_weather),
                contentDescription = "Weather icon",
            )
            Text(
                text = time,
                style = TextStyle(fontSize = 16.sp, color = colorScheme.onBackground)
            )
        }
    }
}

@Composable
private fun HourTemp(temp: String) {
    Text(
        text = buildAnnotatedString {
            withStyle(
                style = SpanStyle(
                    fontSize = 16.0.sp,
                    fontWeight = FontWeight(700.0.toInt()),
                    fontStyle = FontStyle.Normal,
                )
            ) {
                append(temp)
            }
            withStyle(
                style = SpanStyle(
                    baselineShift = BaselineShift.Superscript,
                    fontSize = 8.0.sp,
                    fontWeight = FontWeight(300.0.toInt()),
                    fontStyle = FontStyle.Normal,
                )
            ) { // AnnotatedString.Builder
                append(" o")
            }
            withStyle(
                style = SpanStyle(
                    fontSize = 16.0.sp,
                    fontWeight = FontWeight(300.0.toInt()),
                    fontStyle = FontStyle.Normal,
                )
            ) {
                append("C")
            }
        },
        style = TextStyle(fontSize = 32.sp, color = colorScheme.onBackground),
    )
}


@Preview
@Composable
fun CurrentWeatherPreview() {
//    CurrentWeatherScreen()
}
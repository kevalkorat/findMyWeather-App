package com.example.findmyweatherapp.data.network

import androidx.lifecycle.LiveData
import com.example.findmyweatherapp.data.network.response.CurrentWeatherResponse
import com.example.findmyweatherapp.data.network.response.FutureWeatherResponse

interface WeatherNetworkDataSource {
    val downloadedCurrentWeather: LiveData<CurrentWeatherResponse>
    val downloadedFutureWeather: LiveData<FutureWeatherResponse>

    suspend fun fetchCurrentWeather(
        location: String,
        units: String
    )

    suspend fun fetchFutureWeather(
            location: String,
            units: String,
    )
}
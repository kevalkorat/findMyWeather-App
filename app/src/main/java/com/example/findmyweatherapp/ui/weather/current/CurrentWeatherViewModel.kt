package com.example.findmyweatherapp.ui.weather.current

import androidx.lifecycle.ViewModel
import com.example.findmyweatherapp.data.repository.ForecastRepository
import com.example.findmyweatherapp.internal.lazyDeferred

class CurrentWeatherViewModel(
    private val forecastRepository: ForecastRepository
) : ViewModel() {
    val cWeather by lazyDeferred {
        forecastRepository.getCurrentWeather()
        //forecastRepository.getWeatherDescription()//added the line
    }

    val weatherDescription by lazyDeferred {
        forecastRepository.getWeatherDescription()
    }
}
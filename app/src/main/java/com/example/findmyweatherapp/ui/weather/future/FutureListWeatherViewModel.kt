package com.example.findmyweatherapp.ui.weather.future

import androidx.lifecycle.ViewModel
import com.example.findmyweatherapp.data.repository.ForecastRepository
import com.example.findmyweatherapp.internal.lazyDeferred

class FutureListWeatherViewModel(
        private val forecastRepository: ForecastRepository
) : ViewModel() {

    val weatherEntries by lazyDeferred {
        forecastRepository.getFutureWeatherList()
    }

}
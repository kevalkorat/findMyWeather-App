package com.example.findmyweatherapp.ui.weather.future

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.findmyweatherapp.data.repository.ForecastRepository

class FutureListWeatherViewModelFactory(
        private val forecastRepository: ForecastRepository
): ViewModelProvider.NewInstanceFactory() {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return FutureListWeatherViewModel(forecastRepository) as T
    }
}
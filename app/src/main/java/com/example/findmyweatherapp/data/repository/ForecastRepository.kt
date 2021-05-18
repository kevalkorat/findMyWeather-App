package com.example.findmyweatherapp.data.repository

import androidx.lifecycle.LiveData
import com.example.findmyweatherapp.data.db.entity.CurrentWeatherEntry
import com.example.findmyweatherapp.data.db.entity.FutureWeatherDescription
import com.example.findmyweatherapp.data.db.entity.FutureWeatherEntries
import com.example.findmyweatherapp.data.db.entity.Weather
import com.example.findmyweatherapp.data.db.unitLocalized.UnitSpecificCurrentWeatherEntry

interface ForecastRepository {
    suspend fun getCurrentWeather(): LiveData<out CurrentWeatherEntry>
    suspend fun getWeatherDescription(): LiveData<out Weather>
    suspend fun getFutureWeatherList(): LiveData<out List<FutureWeatherEntries>>
}
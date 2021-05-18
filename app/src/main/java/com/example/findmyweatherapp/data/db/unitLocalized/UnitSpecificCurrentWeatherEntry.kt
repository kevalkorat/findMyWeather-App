package com.example.findmyweatherapp.data.db.unitLocalized

import com.google.gson.annotations.SerializedName

interface UnitSpecificCurrentWeatherEntry {

    val feelsLikeTemperature: Double
    val humidity: Int
    val pressure: Int
    val temperature: Double
    val tempMax: Double
    val tempMin: Double


}
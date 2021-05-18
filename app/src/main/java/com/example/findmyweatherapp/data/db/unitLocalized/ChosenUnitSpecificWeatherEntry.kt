package com.example.findmyweatherapp.data.db.unitLocalized

import androidx.room.ColumnInfo
import androidx.room.RoomWarnings

data class ChosenUnitSpecificWeatherEntry (

    @ColumnInfo(name = "feelsLike", defaultValue = "")
    override val feelsLikeTemperature: Double,
    @ColumnInfo(name = "humidity", defaultValue = "")
    override val humidity: Int,
    @ColumnInfo(name = "pressure", defaultValue = "")
    override val pressure: Int,
    @ColumnInfo(name = "temp", defaultValue = "")
    override val temperature: Double,
    @ColumnInfo(name = "tempMax", defaultValue = "")
    override val tempMax: Double,
    @ColumnInfo(name = "tempMin", defaultValue = "")
    override val tempMin: Double,

) : UnitSpecificCurrentWeatherEntry
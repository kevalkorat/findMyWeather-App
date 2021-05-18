package com.example.findmyweatherapp.data.db.entity


import androidx.room.*
import com.google.gson.annotations.SerializedName

const val CURRENT_WEATHER_ID  = 0


@Entity(tableName = "current_weather")
data class CurrentWeatherEntry(
    @SerializedName("feels_like")
    val feelsLike: Double = 0.0,
    @SerializedName("humidity")
    val humidity: Int = 0,
    @SerializedName("pressure")
    val pressure: Int = 0,
    @SerializedName("temp")
    val temp: Double = 0.0,
    @SerializedName("temp_max")
    val tempMax: Double = 0.0,
    @SerializedName("temp_min")
    val tempMin: Double = 0.0,
    @PrimaryKey(autoGenerate = false)
    val id: Int = CURRENT_WEATHER_ID

) {
//    @PrimaryKey(autoGenerate = false)
//    var id: Int = CURRENT_WEATHER_ID
    constructor() : this(0.0, 0, 0, 0.0, 0.0, 0.0, 0)
}

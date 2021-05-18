package com.example.findmyweatherapp.data.network.response


import com.example.findmyweatherapp.data.db.entity.FutureWeatherEntries
import com.google.gson.annotations.SerializedName

data class FutureWeatherResponse(
        val cnt: Int,
        @SerializedName(value = "list")
        val futureWeatherEntries: List<FutureWeatherEntries>
)
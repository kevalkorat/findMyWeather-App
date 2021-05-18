package com.example.findmyweatherapp.data.db.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName


data class FutureWeatherDescription(
        @SerializedName("description")
        val weatherDescription: String,
        val icon: String
)
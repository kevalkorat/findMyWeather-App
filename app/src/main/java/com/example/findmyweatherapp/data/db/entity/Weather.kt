package com.example.findmyweatherapp.data.db.entity


import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

const val WEATHER_LOCATION_ID = 0

@Entity(tableName = "weather_description")
data class Weather(

    val description: String,
    val icon: String,
    @SerializedName(value = "id")
    val api_id: Int,
    val main: String
){
    //since we are only going to have one instance of weather location inside our db at any given time
    @PrimaryKey(autoGenerate = false)
    var pid: Int = WEATHER_LOCATION_ID
}
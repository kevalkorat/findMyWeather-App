package com.example.findmyweatherapp.data.db

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.findmyweatherapp.data.db.entity.WEATHER_LOCATION_ID
import com.example.findmyweatherapp.data.db.entity.Weather

@Dao
interface WeatherDescriptionDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun upsert(weather: Weather)

    @Query(value = "select * from weather_description where pid=$WEATHER_LOCATION_ID")
    fun getWeatherDescription(): LiveData<Weather>


}
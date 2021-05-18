package com.example.findmyweatherapp.data.db

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.findmyweatherapp.data.db.entity.CURRENT_WEATHER_ID
import com.example.findmyweatherapp.data.db.entity.CurrentWeatherEntry
import com.example.findmyweatherapp.data.db.unitLocalized.ChosenUnitSpecificWeatherEntry


@Dao
interface CurrentWeatherDao {

    //insert or update the entry inside the database
    @Insert(onConflict = OnConflictStrategy.REPLACE) //if conflict happens, it will replace it since we only have one entry at any given time
    fun upsert(weatherEntry: CurrentWeatherEntry)


    //function to get the weather
//    @Query(value = "select * from current_weather where id = $CURRENT_WEATHER_ID")
//    fun getWeather(): LiveData<ChosenUnitSpecificWeatherEntry>

    @Query(value = "select * from current_weather where id = $CURRENT_WEATHER_ID")
    fun getWeather(): LiveData<CurrentWeatherEntry>


}
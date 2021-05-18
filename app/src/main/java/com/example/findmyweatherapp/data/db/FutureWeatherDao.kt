package com.example.findmyweatherapp.data.db

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.findmyweatherapp.data.db.entity.FutureWeatherEntries

@Dao
interface FutureWeatherDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(futureWeatherEntries: List<FutureWeatherEntries>)

    @Query("select * from future_weather")
    fun getFutureWeatherForecast(): LiveData<List<FutureWeatherEntries>>

    @Query("delete from future_weather")
    fun deleteOldEntries()

}
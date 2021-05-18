package com.example.findmyweatherapp.data.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.findmyweatherapp.data.db.entity.*

//we turn this class into a singleton since we don't need multiple instances of our database

@Database(
    entities = [CurrentWeatherEntry::class, Weather::class, FutureWeatherEntries::class],
    version = 1
)

@TypeConverters(Converters::class)
abstract class  ForecastDatabase: RoomDatabase() {

    abstract fun currentWeatherDao(): CurrentWeatherDao
    abstract fun weatherDao(): WeatherDescriptionDao
    abstract fun futureWeatherDao(): FutureWeatherDao
//    abstract fun futureWeatherDescriptionDao(): FutureWeatherDescriptionDao

    companion object {
        @Volatile private var instance: ForecastDatabase? = null
        private val LOCK = Any()

        operator fun invoke(context: Context) = instance ?: synchronized(LOCK){
            instance ?: buildDatabase(context).also { instance = it }
        }

        private fun buildDatabase(context: Context) =
            Room.databaseBuilder(
                context.applicationContext,
                ForecastDatabase::class.java, "forecast.db"
            )
                .build()

    }

}
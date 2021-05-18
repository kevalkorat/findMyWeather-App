package com.example.findmyweatherapp.data.repository

import android.content.ContentValues.TAG
import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.lifecycle.LiveData
import com.example.findmyweatherapp.data.db.CurrentWeatherDao
import com.example.findmyweatherapp.data.db.FutureWeatherDao
import com.example.findmyweatherapp.data.db.WeatherDescriptionDao
import com.example.findmyweatherapp.data.db.entity.CurrentWeatherEntry
import com.example.findmyweatherapp.data.db.entity.FutureWeatherEntries
import com.example.findmyweatherapp.data.db.entity.Weather
import com.example.findmyweatherapp.data.db.unitLocalized.UnitSpecificCurrentWeatherEntry
import com.example.findmyweatherapp.data.network.WeatherNetworkDataSource
import com.example.findmyweatherapp.data.network.response.CurrentWeatherResponse
import com.example.findmyweatherapp.data.network.response.FutureWeatherResponse
import com.example.findmyweatherapp.ui.weather.current.cityName
import com.example.findmyweatherapp.ui.weather.current.unitsSystem
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.time.ZonedDateTime
import java.time.ZonedDateTime.now

class ForecastRepositoryImpl(
    private val currentWeatherDao: CurrentWeatherDao,
    private val weatherNetworkDataSource: WeatherNetworkDataSource,
    private val weatherDescriptionDao: WeatherDescriptionDao,
    private val futureWeatherDao: FutureWeatherDao,



    ) : ForecastRepository {

    init {
        weatherNetworkDataSource.apply {
            downloadedCurrentWeather.observeForever{ newCurrentWeather ->
                //persist
                persistFetchedCurrentWeather((newCurrentWeather))

            }

            downloadedFutureWeather.observeForever{ newFutureWeather ->
                //persist
                persistFetchedFutureWeather((newFutureWeather))

            }
        }
    }


    override suspend fun getCurrentWeather(): LiveData<out CurrentWeatherEntry> {
        return withContext(Dispatchers.IO){
            initWeatherData()
            return@withContext currentWeatherDao.getWeather()
        }
    }

    //weather description function
    override suspend fun getWeatherDescription(): LiveData<Weather> {
        return withContext(Dispatchers.IO){
            initWeatherData()
            return@withContext weatherDescriptionDao.getWeatherDescription()
        }
    }

    //future weather list function
    override suspend fun getFutureWeatherList(): LiveData<out List<FutureWeatherEntries>> {
        return withContext(Dispatchers.IO){
            initWeatherData()
            return@withContext futureWeatherDao.getFutureWeatherForecast()
        }
    }


    private fun persistFetchedCurrentWeather(fetchedWeather: CurrentWeatherResponse){
        GlobalScope.launch(Dispatchers.IO) {
            currentWeatherDao.upsert(fetchedWeather.currentWeatherEntry)
            weatherDescriptionDao.upsert(fetchedWeather.weather[0])

//            debugging help from Kishan
            //tempWeatherDecription = fetchedWeather.weather[0].description
            Log.e("TEsttt1", fetchedWeather.weather[0].description);
            Log.e("Imperial", "Unit System ${fetchedWeather.currentWeatherEntry.temp}", )
        }
    }

    private fun persistFetchedFutureWeather(fetchedWeather: FutureWeatherResponse){
        GlobalScope.launch(Dispatchers.IO) {
            //first delete all the old entries from the table
            futureWeatherDao.deleteOldEntries()
            val futureWeatherList = fetchedWeather.futureWeatherEntries
            (futureWeatherList  as ArrayList<FutureWeatherEntries>).removeAt(0)
            futureWeatherDao.insert(futureWeatherList)

        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private suspend fun initWeatherData(){

        if(isFetchCurrentNeeded(now().minusHours(1)))
            fetchCurrentWeather()
    }

    private suspend fun fetchCurrentWeather(){
        Log.d(TAG, "fetchCurrentWeather: method called")
        weatherNetworkDataSource.fetchCurrentWeather(
            location = cityName,
            units = unitsSystem
        )

        weatherNetworkDataSource.fetchFutureWeather(
            location = cityName,
            units = unitsSystem,
        )
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun isFetchCurrentNeeded(lastFetchTime: ZonedDateTime): Boolean{
        val thirtyMinutesAgo = ZonedDateTime.now().minusMinutes(30)
        return lastFetchTime.isBefore(thirtyMinutesAgo)
    }
}
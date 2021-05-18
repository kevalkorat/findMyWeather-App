package com.example.findmyweatherapp.data.network

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.findmyweatherapp.data.network.response.CurrentWeatherResponse
import com.example.findmyweatherapp.data.network.response.FutureWeatherResponse
import com.example.findmyweatherapp.internal.NoConnectivityException

class WeatherNetworkDataSourceImpl(
        private val openWeatherMapApiService: OpenWeatherMapApiService

) : WeatherNetworkDataSource {

    private val _downloadedCurrentWeather = MutableLiveData<CurrentWeatherResponse>()

    override val downloadedCurrentWeather: LiveData<CurrentWeatherResponse>
        get() = _downloadedCurrentWeather

    override suspend fun fetchCurrentWeather(location: String, units: String) {
        try {
            val fetchCurrentWeather = openWeatherMapApiService
                .getCurrentWeather(location, units)
                .await()
            _downloadedCurrentWeather.postValue(fetchCurrentWeather)
        } catch (e: NoConnectivityException) {
            Log.e("Connectivity", "fetchCurrentWeather: No Internet Conncetion", e)
        }
    }

    private val _downloadedFutureWeather = MutableLiveData<FutureWeatherResponse>()
    override val downloadedFutureWeather: LiveData<FutureWeatherResponse>
        get() = _downloadedFutureWeather

    override suspend fun fetchFutureWeather(
            location: String,
            units: String
    )
    {
        try {
            val fetchedFutureWeather = openWeatherMapApiService
                    .getFutureWeather(location, "8", units)
                    .await()
            _downloadedFutureWeather.postValue(fetchedFutureWeather)
        }
        catch (e: NoConnectivityException){
            Log.e("Connectivity", "No internet connection ", e)
        }



    }
}
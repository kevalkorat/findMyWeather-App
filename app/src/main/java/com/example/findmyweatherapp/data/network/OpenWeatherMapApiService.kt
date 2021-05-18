    package com.example.findmyweatherapp.data.network

import com.example.findmyweatherapp.data.network.ConnectivityInterceptor
import com.example.findmyweatherapp.data.network.ConnectivityInterceptorImpl
import com.example.findmyweatherapp.data.network.response.CurrentWeatherResponse
import com.example.findmyweatherapp.data.network.response.FutureWeatherResponse
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import kotlinx.coroutines.Deferred
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query


const val API_KEY = "248e9c9b6c37ce95b888965a62a3cdd8"

//https://api.openweathermap.org/data/2.5/weather?q={city name}&appid={API key}&units=metric
//https://api.openweathermap.org/data/2.5/weather?q=Rajkot&appid=5deee3a334352d8ed9df3a918c218737&units=metric

interface OpenWeatherMapApiService {

    @GET("weather")
    fun getCurrentWeather(
            @Query("q") location: String = "London",
            @Query("units") units: String = "metric" //metric by default
    ): Deferred<CurrentWeatherResponse>

    @GET("forecast/daily")
    fun getFutureWeather(
            @Query("q") location: String = "London",
            @Query("cnt") count: String = "8",
            @Query("units") units: String = "metric"
    ) : Deferred<FutureWeatherResponse>



    companion object {
        operator fun invoke(
            connectivityInterceptor: ConnectivityInterceptor
        ): OpenWeatherMapApiService{
            val requestInterceptor = Interceptor { chain ->
                val url = chain.request()
                        .url()
                        .newBuilder()
                        .addQueryParameter("appid", API_KEY)
                        .build()
                val request = chain.request()
                        .newBuilder()
                        .url(url)
                        .build()
                return@Interceptor chain.proceed(request)

            }

            val okHttpClient = OkHttpClient.Builder()
                    .addInterceptor(connectivityInterceptor)
                    .addInterceptor(requestInterceptor)
                    .build()

            return Retrofit.Builder()
                    .client(okHttpClient)
                    .baseUrl("https://api.openweathermap.org/data/2.5/")
                    .addCallAdapterFactory(CoroutineCallAdapterFactory())
                    .addConverterFactory(GsonConverterFactory.create())
                    .build()
                    .create(OpenWeatherMapApiService::class.java)
        }
    }

}
package com.example.findmyweatherapp

import android.app.Application
import androidx.preference.PreferenceManager
import com.example.findmyweatherapp.data.db.ForecastDatabase
import com.example.findmyweatherapp.data.network.*
import com.example.findmyweatherapp.data.repository.ForecastRepository
import com.example.findmyweatherapp.data.repository.ForecastRepositoryImpl
import com.example.findmyweatherapp.ui.weather.current.CurrentWeatherViewModelFactory
import com.example.findmyweatherapp.ui.weather.future.FutureListWeatherViewModelFactory
import com.jakewharton.threetenabp.AndroidThreeTen
import org.kodein.di.Kodein
import org.kodein.di.KodeinAware
import org.kodein.di.android.x.androidXModule
import org.kodein.di.generic.bind
import org.kodein.di.generic.instance
import org.kodein.di.generic.provider
import org.kodein.di.generic.singleton


class ForecastApplication: Application(), KodeinAware {
    override val kodein by Kodein.lazy {
        import(androidXModule(this@ForecastApplication))

        bind() from singleton { ForecastDatabase(instance()) }
        bind() from singleton { instance<ForecastDatabase>().currentWeatherDao() }
        bind() from singleton { instance<ForecastDatabase>().weatherDao() } //added this line by myself
        bind() from singleton { instance<ForecastDatabase>().futureWeatherDao() } //added this line by myself
        bind<ConnectivityInterceptor>() with singleton { ConnectivityInterceptorImpl(instance()) }
        bind() from singleton { OpenWeatherMapApiService(instance()) }
        bind<WeatherNetworkDataSource>() with singleton { WeatherNetworkDataSourceImpl(instance()) }
        bind<ForecastRepository>() with singleton { ForecastRepositoryImpl(instance(), instance(), instance(), instance()) }
        bind() from provider { CurrentWeatherViewModelFactory(instance()) }
        bind() from provider { FutureListWeatherViewModelFactory(instance()) }

    }

    override fun onCreate() {
        super.onCreate()
        AndroidThreeTen.init(this)
        PreferenceManager.setDefaultValues(this, R.xml.preferences, false)
    }

}
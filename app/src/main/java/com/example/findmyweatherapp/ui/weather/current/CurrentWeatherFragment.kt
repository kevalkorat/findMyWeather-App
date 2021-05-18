package com.example.findmyweatherapp.ui.weather.current

import android.graphics.drawable.Drawable
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.preference.PreferenceManager
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import com.example.findmyweatherapp.R
import com.example.findmyweatherapp.internal.glide.GlideApp
import com.example.findmyweatherapp.ui.base.ScopedFragment
import kotlinx.android.synthetic.main.current_weather_fragment.*
import kotlinx.coroutines.launch
import org.kodein.di.KodeinAware
import org.kodein.di.android.x.kodein
import org.kodein.di.generic.instance

public var cityName = "Surat"
public var unitsSystem = "metric"
public var darkMode = "false"


class CurrentWeatherFragment : ScopedFragment(), KodeinAware {

    override val kodein by kodein()
    private val viewModelFactory: CurrentWeatherViewModelFactory by instance()


    private lateinit var viewModel: CurrentWeatherViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.current_weather_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        loadSettings()
        viewModel = ViewModelProvider(this, viewModelFactory).get(CurrentWeatherViewModel::class.java)

        bindUI()

    }

//    override fun onResume() {
//        super.onResume()
//        Log.d("onResume", "called")
//        bindUI()
//    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        loadSettings()
    }

    private fun bindUI() = launch {
        val currentWeather = viewModel.cWeather.await()
        val weatherDescription = viewModel.weatherDescription.await()

        weatherDescription.observe(viewLifecycleOwner, Observer {
            if (it == null) return@Observer

            updateCondition(it.description.capitalize()) //not executing
//http://openweathermap.org/img/wn/04d@4x.png
            Log.e("glide", "http://openweathermap.org/img/wn/${it.icon}.png"    )
            Glide.with(this@CurrentWeatherFragment)
                .load("https://openweathermap.org/img/wn/${it.icon}@4x.png")
//                .error(R.drawable.ic_clear_sky)
//                .placeholder(R.drawable.ic_clear_sky)
                .into(iv_condition_icon)

        })

        currentWeather.observe(viewLifecycleOwner, Observer {
            if(it == null) return@Observer
            //if the data we observe is not null we will hide the loading indicator and it's text
            group_loading.visibility = View.GONE

            updateLocation(cityName)



            Log.d("Location Description", it.temp.toString())
            updateDateToToday()
            updateTemperatures(it.temp.toInt(), it.feelsLike.toInt(), it.tempMin.toInt(), it.tempMax.toInt())
            //updateCondition(it.)
            updateHumidity(it.humidity)
            updatePressure(it.pressure)



        })
    }

    //helper functions to populate the user interface rather than putting inside the observer and polluting it
    private fun updateLocation(location: String){
        (activity as? AppCompatActivity)?.supportActionBar?.title = location
    }

    private fun updateDateToToday(){
        (activity as? AppCompatActivity)?.supportActionBar?.subtitle = "Today"
    }

    private fun updateTemperatures(temp: Int, feelsLike: Int, tempMin: Int, tempMax: Int){
        val unitAbbreviation = "°"
        tv_temperature.text = "$temp$unitAbbreviation"
        tv_feels_like_temperature.text = "Feels $feelsLike$unitAbbreviation"
        tv_minMax.text = "HI:$tempMax$unitAbbreviation LO:$tempMin$unitAbbreviation"
    }



    private fun updateCondition(condition: String){
        tv_condition.text = condition
    }

    private fun updateHumidity(humidity: Int){
        val unitAbbreviation = "%"
        tv_humidity.text = "Humidity: $humidity$unitAbbreviation"
    }

    private fun updatePressure(pressure: Int){
        val unitAbbreviation = "mm"
        tv_pressure.text = "Pressure : $pressure $unitAbbreviation"
    }

    fun loadSettings(){
        val sp = PreferenceManager.getDefaultSharedPreferences(context)

        val location = sp.getString("CUSTOM_LOCATION", "London")
        val unitPreference = sp.getString("UNIT_SYSTEM", "metric")

        cityName = location!!
        unitsSystem = unitPreference!!


    }





}
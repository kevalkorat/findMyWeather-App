package com.example.findmyweatherapp.ui.weather.future

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.findmyweatherapp.R
import com.example.findmyweatherapp.data.db.entity.FutureWeatherEntries
import com.example.findmyweatherapp.ui.base.ScopedFragment
import com.example.findmyweatherapp.ui.weather.current.CurrentWeatherViewModelFactory
import com.example.findmyweatherapp.ui.weather.current.cityName
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.ViewHolder
import kotlinx.android.synthetic.main.future_list_weather_fragment.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import okhttp3.internal.Internal.instance
import org.kodein.di.Kodein
import org.kodein.di.android.x.kodein
import org.kodein.di.KodeinAware
import org.kodein.di.generic.instance

class FutureListWeatherFragment : ScopedFragment(), KodeinAware {

    override val kodein by kodein()
    private val viewModelFactory: FutureListWeatherViewModelFactory by instance()

    private lateinit var viewModel: FutureListWeatherViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.future_list_weather_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this, viewModelFactory).get(FutureListWeatherViewModel::class.java)
        bindUI()
    }


    private fun bindUI() = launch(Dispatchers.Main) {

        val futureWeatherEntries = viewModel.weatherEntries.await()

        futureWeatherEntries.observe(viewLifecycleOwner, Observer { weatherEntries ->

            if (weatherEntries == null) return@Observer
            group_loading.visibility = View.GONE

            updateDateToNextWeek()
            updateLocation()
            initRecyclerView(weatherEntries.toFutureWeatherItems())


        })

    }

    private fun updateDateToNextWeek(){
        (activity as? AppCompatActivity)?.supportActionBar?.subtitle = "Next Week"
    }

    private fun updateLocation(){
        (activity as? AppCompatActivity)?.supportActionBar?.title = cityName
    }

    private fun List<FutureWeatherEntries>.toFutureWeatherItems() : List<FutureWeatherItem> {
        return this.map {
            FutureWeatherItem(it)
        }
    }


    private fun initRecyclerView(items: List<FutureWeatherItem>){

        val groupAdapter = GroupAdapter<ViewHolder>().apply {
            addAll(items)
        }

        recyclerView.apply {
            layoutManager = LinearLayoutManager(this@FutureListWeatherFragment.context)
            adapter = groupAdapter
        }


    }


}
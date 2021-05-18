package com.example.findmyweatherapp.ui.weather.future

import android.annotation.SuppressLint
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.example.findmyweatherapp.R
import com.example.findmyweatherapp.data.db.entity.FutureWeatherEntries
import com.example.findmyweatherapp.internal.glide.GlideApp
import com.xwray.groupie.kotlinandroidextensions.Item
import com.xwray.groupie.kotlinandroidextensions.ViewHolder
import kotlinx.android.synthetic.main.current_weather_fragment.*
import kotlinx.android.synthetic.main.item_future_weather.view.*

class FutureWeatherItem(
        val weatherEntry: FutureWeatherEntries
        ): Item() {
    override fun bind(viewHolder: ViewHolder, position: Int) {
        viewHolder.itemView.apply {
            this.tv_day.text = weatherEntry.stringToDay()
            this.tv_date.text = weatherEntry.stringToDate()
            this.tv_tempMax.text = weatherEntry.futureTemp.max.toInt().toString() + "°/"
            this.tv_tempMin.text = weatherEntry.futureTemp.min.toInt().toString() + "°"
            this.tv_future_weather_description.text = weatherEntry.futureWeatherDescription[0].weatherDescription.capitalize()




        }
    }

    override fun getLayout() = R.layout.item_future_weather

//    @SuppressLint("CheckResult")
//    private fun ViewHolder.updateConitionImage(){
//        GlideApp.with(this.containerView)
//            .load("https://openweathermap.org/img/wn/" + weatherEntry.futureWeatherDescription[0].icon + "@4x.png")
//            .into()
//
//    }

}
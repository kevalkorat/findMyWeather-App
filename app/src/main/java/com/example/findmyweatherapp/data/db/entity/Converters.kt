package com.example.findmyweatherapp.data.db.entity

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.io.Serializable
import java.lang.reflect.Type

class Converters {

    @TypeConverter
    fun stringToFutureWeatherDescription(json: String?): List<FutureWeatherDescription> {
        val gson = Gson()
        val type: Type = object : TypeToken<List<FutureWeatherDescription?>?>() {}.getType()
        return gson.fromJson<List<FutureWeatherDescription>>(json, type)
    }

    @TypeConverter
    fun futureWeatherDescriptionToString(list: List<FutureWeatherDescription?>?): String {
        val gson = Gson()
        val type: Type = object : TypeToken<List<FutureWeatherDescription?>?>() {}.getType()
        return gson.toJson(list, type)
    }
}
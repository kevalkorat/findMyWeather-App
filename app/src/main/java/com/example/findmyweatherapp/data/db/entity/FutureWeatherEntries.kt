package com.example.findmyweatherapp.data.db.entity

import androidx.room.*
import com.google.gson.annotations.SerializedName
import java.text.SimpleDateFormat
import java.util.*


@Entity(tableName = "future_weather", indices = [Index(value = ["dt"], unique = true)])
data class FutureWeatherEntries(
        val dt: Int,
        @SerializedName(value = "temp")
        @Embedded
        val futureTemp: FutureTemp,
        //@Embedded
        @SerializedName("weather")
        val futureWeatherDescription: List<FutureWeatherDescription>,
        @PrimaryKey(autoGenerate = true)
        val fid: Int? = null
){
        fun stringToDate(): String? {
                try {
                        val sdf = SimpleDateFormat("d MMM yyyy")
                        val netDate = Date(dt.toLong() * (1000))
                        return sdf.format(netDate)
                } catch (e: Exception) {
                        return e.toString()
                }
        }

        fun stringToDay(): String? {
                try {
                        val sdf = SimpleDateFormat("EEEE")
                        val netDate = Date(dt.toLong() * (1000))
                        return sdf.format(netDate)
                } catch (e: Exception) {
                        return e.toString()
                }
        }



}


//object ProductTypeConverters {
//        @TypeConverter
//        fun stringToFutureWeatherDescription(json: String?): List<FutureWeatherDescription> {
//                val gson = Gson()
//                val type: Type = object : TypeToken<List<FutureWeatherDescription?>?>() {}.getType()
//                return gson.fromJson<List<FutureWeatherDescription>>(json, type)
//        }
//
//        @TypeConverter
//        fun futureWeatherDescriptionToString(list: List<FutureWeatherDescription?>?): String {
//                val gson = Gson()
//                val type: Type = object : TypeToken<List<FutureWeatherDescription?>?>() {}.getType()
//                return gson.toJson(list, type)
//        }
//
////        @TypeConverter
////        fun fromFutureWeatherDescription()
//}


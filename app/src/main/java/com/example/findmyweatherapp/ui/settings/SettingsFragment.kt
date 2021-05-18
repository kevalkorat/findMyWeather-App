package com.example.findmyweatherapp.ui.settings

import android.content.Context
import android.content.Context.LOCATION_SERVICE
import android.content.Intent
import android.content.SharedPreferences
import android.content.pm.PackageManager
import android.location.Location
import android.location.LocationManager
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.constraintlayout.motion.widget.Debug.getLocation
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat.getSystemService
import androidx.core.content.getSystemService
import androidx.preference.PreferenceFragmentCompat
import androidx.preference.PreferenceManager
import com.campuswatch.helper.util.LocationFetchUtil
import com.example.findmyweatherapp.R
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.model.LatLng
import java.lang.Exception


class SettingsFragment: PreferenceFragmentCompat(), SharedPreferences.OnSharedPreferenceChangeListener {

    protected var locationFetchUtil: LocationFetchUtil? = null
    private var curLat: Double = 0.0
    private var curLon: Double = 0.0
    //on create method
    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        addPreferencesFromResource(R.xml.preferences)

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        PreferenceManager.getDefaultSharedPreferences(requireActivity())
            .registerOnSharedPreferenceChangeListener(this)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        (activity as? AppCompatActivity)?.supportActionBar?.title = "Settings"
        (activity as? AppCompatActivity)?.supportActionBar?.subtitle = null

    }

    override fun onSharedPreferenceChanged(sharedPreferences: SharedPreferences?, key: String) {

        val sp = PreferenceManager.getDefaultSharedPreferences(context)

        val isDarkModeOn = sp.getBoolean("SET_DARK_THEME", false)
        val isLocationOn = sp.getBoolean("USE_DEVICE_LOCATION", false)


        when(key){
            "SET_DARK_THEME" ->{
                when(isDarkModeOn){
                    true ->{
                        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
                    }
                    false ->{
                        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
                    }
                }
                Log.e("Test", "$isDarkModeOn  $key")
            }

            "USE_DEVICE_LOCATION" ->{
                when(isLocationOn){
                    true ->{
//                        checkLocationPermission()
                    }
                }
                Log.e("Test", "$isLocationOn  $key")
            }


        }
    }

    override fun onDestroy() {
        super.onDestroy()
        PreferenceManager.getDefaultSharedPreferences(requireActivity())
            .unregisterOnSharedPreferenceChangeListener(this)

    }

//    private fun checkLocationPermission() {
//        locationFetchUtil = LocationFetchUtil(requireActivity(),
//            shouldRequestPermissions = true,
//            shouldRequestOptimization = true,
//            callbacks = object : LocationFetchUtil.Callbacks {
//                override fun onSuccess(location: Location) {
//                    location?.let {
//                        if (!it.latitude.isNaN())
//                            curLat = it.latitude
//                        if (!it.longitude.isNaN())
//                            curLon = it.longitude
//                    }
//                }
//
//                override fun onFailed(locationFailedEnum: LocationFetchUtil.LocationFailedEnum) {
//                }
//            })
//    }
//
////    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
////        locationFetchUtil?.onActivityResult(requestCode, resultCode, data)
////
////        super.onActivityResult(requestCode, resultCode, data)
////    }
////
//    override fun onRequestPermissionsResult(
//        requestCode: Int,
//        permissions: Array<out String>,
//        grantResults: IntArray
//    ) {
//    when (requestCode) {
//        REQUEST_CODE_ASK_PERMISSIONS -> if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
//            getLocation()
//        } else {
//            // Permission Denied
////
//
//        }
//        else -> super.onRequestPermissionsResult(requestCode, permissions, grantResults)
//    }
//        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
//    }
//    private val REQUEST_CODE_ASK_PERMISSIONS = 123
//
//        //Get location
//        fun getLocation() {
//            try {
//                val locationManager: LocationManager =
////                    getSystemService(requireActivity(), requireActivity().getSystemService()) as LocationManager
//
//                var myLocation: Location? =
//                    locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER)
//                if (myLocation == null) {
//                    myLocation =
//                        locationManager.getLastKnownLocation(LocationManager.PASSIVE_PROVIDER)
//                }
//            }catch(e:SecurityException){
//                e.printStackTrace()
//            }
//        }


}
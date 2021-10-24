package com.jorgegomezdeveloper.waycommerce.util.location

import android.Manifest
import android.app.Activity
import android.content.Context
import android.content.pm.PackageManager
import android.location.Location
import android.location.LocationManager
import android.os.Bundle
import android.util.Log
import androidx.core.app.ActivityCompat
import java.lang.Exception

/**
 *   @author Jorge G.A.
 *   @since 24/10/2021
 *   @email jorgegomezdeveloper@gmail.com
 *
 *   Util class for the control of gps for get the location.
 */
class GpsUtil: android.location.LocationListener {

// =================================================================================================
// CONSTANTS
// =================================================================================================

    companion object {
        //The minimum distance to change Updates in meters.
        //10 meters.
        private const val MIN_DISTANCE_CHANGE_FOR_UPDATES: Long = 10
        //The minimum time between updates in milliseconds.
        //1 minute.
        private const val MIN_TIME_BW_UPDATES: Long = 60000
    }

// =================================================================================================
// Attributes
// =================================================================================================

    private var context: Context? = null

    private var isGPSEnabled = false
    private var isNetworkEnabled = false
    private var canGetLocation = false

    private var location: Location? = null
    private var latitude = 0.0
    private var longitude = 0.0

    private var locationManager: LocationManager? = null

    private var instance: GpsUtil? = null


// =================================================================================================
// Config: START GPS AND GET LOCATION
// =================================================================================================

    /**
     * Initialize the service of gps.
     * Ang get the current location.
     */
    open fun startGpsService(context: Context): Location? {

        this.context = context

        return try {
            //Getting object of location service.
            locationManager = context.getSystemService(Context.LOCATION_SERVICE) as LocationManager
            //Getting Gps status.
            isGPSEnabled = locationManager!!.isProviderEnabled(LocationManager.GPS_PROVIDER)
            //Getting network status.
            isNetworkEnabled = locationManager!!.isProviderEnabled(LocationManager.NETWORK_PROVIDER)
            //Check permissions.
            checkPermissions()
            //Check status of network and gps.
            if (checkStatusNetworkGps()) {
                createLocation()
            }
            //Create and get location.
            location = createLocation()
            location
        } catch (exception: Exception) {
            exception.printStackTrace();
            null
        }
    }

    /**
     * Check permissions for a location ok.
     */
    private fun checkPermissions() {

        if (ActivityCompat.checkSelfPermission(context!!,
                Manifest.permission.ACCESS_FINE_LOCATION)
            != PackageManager.PERMISSION_GRANTED
            && ActivityCompat.checkSelfPermission(
                context!!,
                Manifest.permission.ACCESS_COARSE_LOCATION
            )
            != PackageManager.PERMISSION_GRANTED
        ) {
            requestPermissions()
        }
    }

    /**
     * Check status of the network and Gps.
     */
    private fun checkStatusNetworkGps(): Boolean {
        return !(!isGPSEnabled && !isNetworkEnabled)
    }

    /**
     * Create and get the location with latitude and longitude.
     */
    private fun createLocation(): Location? {

        canGetLocation = true

        if (isNetworkEnabled && isGPSEnabled) {
            if (ActivityCompat.checkSelfPermission(
                    context!!,
                    Manifest.permission.ACCESS_FINE_LOCATION
                )
                != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(
                    context!!,
                    Manifest.permission.ACCESS_COARSE_LOCATION
                )
                != PackageManager.PERMISSION_GRANTED
            ) {
                requestPermissions()
            }
            locationManager!!.requestLocationUpdates(
                LocationManager.NETWORK_PROVIDER,
                MIN_TIME_BW_UPDATES,
                MIN_DISTANCE_CHANGE_FOR_UPDATES.toFloat(), this
            )
            val providers = locationManager!!.getProviders(true)
            if (locationManager != null) {
                for (provider in providers) {
                    val lastKnownLocation = locationManager!!.getLastKnownLocation(provider)!!
                    if (location == null ||
                        lastKnownLocation.accuracy < location!!.accuracy
                    ) {
                        location = lastKnownLocation
                    }
                }
                if (location != null) {
                    latitude = location!!.latitude
                    longitude = location!!.longitude
                }
            }

            //If GPS enabled, get latitude/longitude using GPS Services.
            if (isGPSEnabled) {
                if (location == null) {
                    assert(locationManager != null)
                    locationManager!!.requestLocationUpdates(
                        LocationManager.GPS_PROVIDER,
                        MIN_TIME_BW_UPDATES,
                        MIN_DISTANCE_CHANGE_FOR_UPDATES.toFloat(), this
                    )
                    if (locationManager != null) {
                        location =
                            locationManager!!.getLastKnownLocation(LocationManager.GPS_PROVIDER)
                        if (location != null) {
                            latitude = location!!.latitude
                            longitude = location!!.longitude
                        }
                    }
                }
            }
        }
        return location
    }

    private fun requestPermissions() {
        val permissions = arrayOf(
            Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.ACCESS_COARSE_LOCATION
        )
        try {
            ActivityCompat.requestPermissions((context as Activity?)!!, permissions, 1)
        } catch (exception: Exception) {
            exception.printStackTrace();
        }
    }

    /**
     * Get the object with the location of user.
     */
    open fun getLocation(): Location? {
        return location
    }

// =================================================================================================
// Methods events and status
// =================================================================================================

    /**
     * Stop using GPS listener.
     * Calling this function will stop using GPS in your app.
     */
    open fun stopUsingGPS() {
        if (locationManager != null) {
            locationManager!!.removeUpdates(this)
        }
    }

    override fun onLocationChanged(location: Location) {
        Log.d("GPS", "CHANGE LOCATION")
        latitude = location.latitude
        longitude = location.longitude
        Log.d("GPS", "Latitude: $latitude")
        Log.d("GPS", "Longitude: $longitude")
        this.location = location
    }

    override fun onStatusChanged(provider: String?, status: Int, extras: Bundle?) {
        Log.d("GPS", "CHANGE STATUS")
    }
}

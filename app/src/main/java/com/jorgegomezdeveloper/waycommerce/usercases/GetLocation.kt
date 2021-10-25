package com.jorgegomezdeveloper.waycommerce.usercases

import android.content.Context
import android.location.Location
import androidx.lifecycle.LiveData
import com.jorgegomezdeveloper.waycommerce.util.location.GpsUtil

/**
 *   @author Jorge G.A.
 *   @since 25/10/2021
 *   @email jorgegomezdeveloper@gmail.com
 *
 *   Case use for connect with location data of gps.
 */
class GetLocation(private val gpsUtil: GpsUtil,
                  private val context: Context) {

    fun execute(): LiveData<Location>? = gpsUtil.startGpsService(context)
}
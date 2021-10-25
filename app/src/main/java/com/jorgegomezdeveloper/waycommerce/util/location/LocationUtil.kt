package com.jorgegomezdeveloper.waycommerce.util.location

import android.location.Location

/**
 *   @author Jorge G.A.
 *   @since 24/10/2021
 *   @email jorgegomezdeveloper@gmail.com
 *
 *   Util class for get the distance with the user.
 */
class LocationUtil {

// =================================================================================================
// Location methods
// =================================================================================================

    /**
     * Get the distance between user and other.
     */
    fun getDistanceBetweenUserAndOther(
        locationUser: Location,
        latitudeOther: Double,
        longitudeOther: Double): Int {

        val distanceResult = FloatArray(2)

        Location.distanceBetween(
            locationUser.latitude, locationUser.longitude,
            latitudeOther, longitudeOther,
            distanceResult)

        return if (distanceResult[0] >= 1000) {
            val distanceKm = distanceResult[0].div(1000).toInt()
            distanceKm
        } else {
            val distanceMeters = distanceResult[0].toInt()
            distanceMeters
        }
    }
}
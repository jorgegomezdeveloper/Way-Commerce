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
        longitudeOther: Double): Float {

        val distanceResult = FloatArray(2)

        Location.distanceBetween(
            locationUser.latitude, locationUser.longitude,
            latitudeOther, longitudeOther,
            distanceResult)

        return distanceResult[0]
    }
}
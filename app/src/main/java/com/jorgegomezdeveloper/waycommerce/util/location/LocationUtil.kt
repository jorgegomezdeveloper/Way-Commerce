package com.jorgegomezdeveloper.waycommerce.util.location

import android.location.Location
import com.jorgegomezdeveloper.waycommerce.common.constants.Constants

/**
 *   @author Jorge G.A.
 *   @since 24/10/2021
 *   @email jorgegomezdeveloper@gmail.com
 *
 *   Util class for get the distance with the user between locations.
 */
object LocationUtil {

// =================================================================================================
// Location methods
// =================================================================================================

    /**
     * Get the distance between user and others.
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

        return if (distanceResult[0] >= Constants.THOUSAND) {
            val distanceKm = distanceResult[0].div(Constants.THOUSAND).toInt()
            distanceKm
        } else {
            val distanceMeters = distanceResult[0].toInt()
            distanceMeters
        }
    }
}
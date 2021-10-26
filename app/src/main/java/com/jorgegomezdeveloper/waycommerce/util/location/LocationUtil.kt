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
        longitudeOther: Double): Float {

        val distanceResult = FloatArray(2)

        Location.distanceBetween(
            locationUser.latitude, locationUser.longitude,
            latitudeOther, longitudeOther,
            distanceResult)

        return distanceResult[0]
    }

    /**
     * Get true or false it the distance is or not kilometres.
     */
    fun areKilometres(distance: Float): Boolean {
        return distance >= Constants.ONE_KILOMETRE_IN_METERS
    }

    /**
     * Convert the distance to integer in kilometres or meters.
     */
    fun convertDistance(distance: Float): Int {

        return if (distance >= Constants.ONE_KILOMETRE_IN_METERS) {
            val distanceKm = distance.div(Constants.ONE_KILOMETRE_IN_METERS).toInt()
            distanceKm
        } else {
            val distanceMeters = distance.toInt()
            distanceMeters
        }
    }
}
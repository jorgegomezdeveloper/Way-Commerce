package com.jorgegomezdeveloper.waycommerce.ui.features.listcommerces.viewmodel

import android.content.Context
import android.location.Location
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.jorgegomezdeveloper.waycommerce.data.network.resource.Resource
import com.jorgegomezdeveloper.waycommerce.model.Commerce
import com.jorgegomezdeveloper.waycommerce.ui.base.WCBaseFragment
import com.jorgegomezdeveloper.waycommerce.ui.base.WCBaseViewModel
import com.jorgegomezdeveloper.waycommerce.usercases.GetCommerces
import com.jorgegomezdeveloper.waycommerce.usercases.GetLocation
import com.jorgegomezdeveloper.waycommerce.util.location.GpsUtil
import com.jorgegomezdeveloper.waycommerce.util.location.LocationUtil
import java.util.*
import java.util.Collections.sort
import kotlin.Comparator

/**
 *   @author Jorge G.A.
 *   @since 21/10/2021
 *   @email jorgegomezdeveloper@gmail.com
 *
 *   View model class for the data of the list commerces.
 */
class WCListCommercesViewModel: WCBaseViewModel() {

// =================================================================================================
// Enums
// =================================================================================================

    enum class Categories {
        ALL,
        BEAUTY,
        FOOD,
        LEISURE,
        SHOPPING
    }

// =================================================================================================
// Attributes
// =================================================================================================

    //Commerces.
    private var getCommercesMutableLiveData: MutableLiveData<List<Commerce>> = MutableLiveData()
    //Location.
    private var getLocationMutableLiveData: MutableLiveData<Location> = MutableLiveData()

    //Others
    private var commercesCurrent: List<Commerce>? = listOf()

// =================================================================================================
// Use case: Get commerces
// =================================================================================================

    fun getCommerces(
        getCommerce: GetCommerces,
        fragment: WCBaseFragment) {

        getCommerce.execute()
            .observe(fragment, { resource ->

                if (resource != null) {

                    when (resource.status) {

                        Resource.Status.SUCCESS -> {
                            getCommercesMutableLiveData.value = resource.data as List<Commerce>
                        }
                        Resource.Status.DATA_NOT_AVAILABLE -> {
                            getCommercesMutableLiveData.value = null
                        }
                        Resource.Status.ERROR -> {
                            getCommercesMutableLiveData.value = null
                        }
                    }
                }
            })
    }

    fun setCommercesMutableLiveData(getCommercesMutableLiveData: MutableLiveData<List<Commerce>>) {
        this.getCommercesMutableLiveData = getCommercesMutableLiveData
    }

    fun getCommercesMutableLiveData(): MutableLiveData<List<Commerce>> {
        return getCommercesMutableLiveData
    }

// =================================================================================================
// Filter collections
// =================================================================================================

    /**
     * Get the list of commerces filtered by category.
     */
    fun getListCommercesByCategory(category: String): List<Commerce>? {

        return when (category) {
            Categories.ALL.name -> {
                commercesCurrent = getCommercesMutableLiveData.value
                return commercesCurrent
            }
            Categories.BEAUTY.name,
            Categories.FOOD.name,
            Categories.LEISURE.name,
            Categories.SHOPPING.name -> {
                commercesCurrent =
                filterListCommercesByCategory(category)
                return commercesCurrent
            }
            else ->
                getCommercesMutableLiveData().value
        }
    }

    /**
     * Filter the list of commerces by category from main collection.
     */
    private fun filterListCommercesByCategory(category: String): List<Commerce>? {

        val listCommerces: List<Commerce>? = getCommercesMutableLiveData.value
        return listCommerces?.filter { it.category == category }
    }

// =================================================================================================
// Use case: Get location
// =================================================================================================

    fun getLocation(
        gpsUtil: GpsUtil,
        locationUtil: LocationUtil,
        fragment: WCBaseFragment) {

        execute(
            gpsUtil,
            fragment.context)!!
            .observe(fragment, { location ->

            if (location != null) {
                getLocationMutableLiveData.value = location
                gpsUtil.stopUsingGPS()
                buildDistanceCommerceWithUser(location, locationUtil)
            }
        })
    }

    private fun execute(gpsUtil: GpsUtil,
                        context: Context?): LiveData<Location>? = gpsUtil.startGpsService(context!!)

    fun setLocationMutableLiveData(getLocationMutableLiveData: MutableLiveData<Location>) {
        this.getLocationMutableLiveData = getLocationMutableLiveData
    }

    fun getLocationMutableLiveData(): MutableLiveData<Location> {
        return getLocationMutableLiveData
    }


// =================================================================================================
// Order collections
// =================================================================================================

    private fun buildDistanceCommerceWithUser(
        locationUser: Location,
        locationUtil: LocationUtil) {

        val commerces: List<Commerce> = commercesCurrent as List<Commerce>

        if (commerces.isNotEmpty()) {

            for (commerce: Commerce in commerces) {

                commerce.distance =
                locationUtil.getDistanceBetweenUserAndOther(
                    locationUser,
                    commerce.latitude!!,
                    commerce.longitude!!)
            }
        }

        //Order commerces with distance.
        commercesCurrent = getListCommercesOrderByDistance(commerces)
        Log.i("ORDER", commercesCurrent.toString())
    }

    /**
     * Get the list of commerces filtered by category.
     */
    private fun getListCommercesOrderByDistance(commerces: List<Commerce>): List<Commerce> {
        return orderListCommercesByDistance(commerces)
    }

    /**
     * Filter the list of commerces by category from main collection.
     */
    private fun orderListCommercesByDistance(commerces: List<Commerce>): List<Commerce> {

        sort(commerces
        ) { commerce1, commerce2 -> commerce1.distance!!.compareTo(commerce2.distance!!) }

        //Get list ordered.
        return commerces
    }
}
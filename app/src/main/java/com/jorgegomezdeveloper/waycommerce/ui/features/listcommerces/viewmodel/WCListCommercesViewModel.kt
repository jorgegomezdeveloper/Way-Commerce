package com.jorgegomezdeveloper.waycommerce.ui.features.listcommerces.viewmodel

import android.content.Context
import android.location.Location
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.jorgegomezdeveloper.waycommerce.data.network.resource.Resource
import com.jorgegomezdeveloper.waycommerce.model.Commerce
import com.jorgegomezdeveloper.waycommerce.ui.base.WCBaseFragment
import com.jorgegomezdeveloper.waycommerce.ui.base.WCBaseViewModel
import com.jorgegomezdeveloper.waycommerce.usercases.GetCommerces
import com.jorgegomezdeveloper.waycommerce.util.location.GpsUtil
import com.jorgegomezdeveloper.waycommerce.util.location.LocationUtil
import java.util.Collections.sort

/**
 *   @author Jorge G.A.
 *   @since 21/10/2021
 *   @email jorgegomezdeveloper@gmail.com
 *
 *   View model class for the data of the list commerces.
 */
open class WCListCommercesViewModel: WCBaseViewModel() {

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

    //Collections
    private var commercesCurrent: List<Commerce>? = ArrayList()
    private var commercesCurrentOrdered: List<Commerce>? = ArrayList()

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
                            commercesCurrent = getCommercesMutableLiveData.value
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
                commercesCurrent =
                    getCommercesMutableLiveData.value
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
            else -> getCommercesMutableLiveData.value
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
// Get location
// =================================================================================================

    fun getLocation(
        gpsUtil: GpsUtil,
        fragment: WCBaseFragment) {

        execute(
            gpsUtil,
            fragment.context)!!
            .observe(fragment, { location ->

            if (location != null) {
                gpsUtil.stopUsingGPS()
                buildDistanceCommerceWithUser(location)
            }
        })
    }

    private fun execute(gpsUtil: GpsUtil,
                        context: Context?): LiveData<Location>? = gpsUtil.startGpsService(context!!)

// =================================================================================================
// Order collections
// =================================================================================================

    private fun buildDistanceCommerceWithUser(
        locationUser: Location) {

        val commerces: List<Commerce> = commercesCurrent as List<Commerce>

        if (commerces.isNotEmpty()) {

            (commercesCurrentOrdered as ArrayList).clear()

            for (commerce: Commerce in commerces) {

                commerce.distance =
                LocationUtil.getDistanceBetweenUserAndOther(
                    locationUser,
                    commerce.latitude!!,
                    commerce.longitude!!)

                (commercesCurrentOrdered as ArrayList).add(commerce)
            }
        }

        //Order commerces with distance.
        commercesCurrentOrdered = getListCommercesOrderByDistance(commercesCurrentOrdered!!)
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

// =================================================================================================
// Methods collections of commerces
// =================================================================================================

    fun getCommercesCurrent(): List<Commerce>? {
        return commercesCurrent
    }

    fun getCommercesCurrentOrdered(): List<Commerce>? {
        return commercesCurrentOrdered
    }
}
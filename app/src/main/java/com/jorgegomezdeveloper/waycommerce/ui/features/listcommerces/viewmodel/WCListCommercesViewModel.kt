package com.jorgegomezdeveloper.waycommerce.ui.features.listcommerces.viewmodel

import androidx.lifecycle.MutableLiveData
import com.jorgegomezdeveloper.waycommerce.data.network.resource.Resource
import com.jorgegomezdeveloper.waycommerce.model.Commerce
import com.jorgegomezdeveloper.waycommerce.ui.base.WCBaseFragment
import com.jorgegomezdeveloper.waycommerce.ui.base.WCBaseViewModel
import com.jorgegomezdeveloper.waycommerce.usercases.GetCommerces

/**
 *   @author Jorge G.A.
 *   @since 21/10/2021
 *   @email jorgegomezdeveloper@gmail.com
 *
 *   View model class for the data of the list commerces.
 */
class WCListCommercesViewModel: WCBaseViewModel() {

// =================================================================================================
// Enum
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

    private var getCommercesMutableLiveData: MutableLiveData<List<Commerce>> = MutableLiveData()

// =================================================================================================
// USE CASES
// =================================================================================================

    fun getCommerces(getCommerce: GetCommerces, fragment: WCBaseFragment) {

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
// FILTER COLLECTIONS
// =================================================================================================

    /**
     * Get the list of commerces filtered by category.
     */
    fun getListCommercesByCategory(category: String): List<Commerce>? {

        return when (category) {
            Categories.ALL.name -> {
                getCommercesMutableLiveData().value
            }
            Categories.BEAUTY.name,
            Categories.FOOD.name,
            Categories.LEISURE.name,
            Categories.SHOPPING.name -> {
                filterListCommercesByCategory(category)
            }
            else -> getCommercesMutableLiveData().value
        }
    }

    /**
     * Filter the list of commerces by category from main collection.
     */
    private fun filterListCommercesByCategory(category: String): List<Commerce>? {

        val listCommerces: List<Commerce>? = getCommercesMutableLiveData.value
        return listCommerces?.filter { it.category == category }
    }
}
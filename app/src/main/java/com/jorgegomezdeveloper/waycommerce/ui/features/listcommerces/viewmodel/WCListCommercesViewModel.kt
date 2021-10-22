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
                        Resource.Status.ERROR->{
                            getCommercesMutableLiveData.value = null
                        }
                    }
                }
            })
    }

    fun setCommercesMutableLiveData(getCommercesMutableLiveData: MutableLiveData<List<Commerce>>) {
        this.getCommercesMutableLiveData = getCommercesMutableLiveData
    }

    fun getCommercesMutableLiveData() : MutableLiveData<List<Commerce>> {
        return getCommercesMutableLiveData
    }
}
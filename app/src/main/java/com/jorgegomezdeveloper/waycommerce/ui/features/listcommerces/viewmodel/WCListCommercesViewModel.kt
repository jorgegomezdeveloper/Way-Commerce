package com.jorgegomezdeveloper.waycommerce.ui.features.listcommerces.viewmodel

import androidx.lifecycle.MutableLiveData
import com.jorgegomezdeveloper.waycommerce.data.network.resource.Resource
import com.jorgegomezdeveloper.waycommerce.model.CommerceModel
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

    private var getCommercesMutableLiveData: MutableLiveData<CommerceModel> = MutableLiveData()

// =================================================================================================
// USE CASES
// =================================================================================================

    fun getCommerces(getComic: GetCommerces, fragment: WCBaseFragment) {

        getComic.execute()
            .observe(fragment, { resource ->

                if (resource != null) {

                    when (resource.status) {

                        Resource.Status.SUCCESS -> {
                            getCommercesMutableLiveData.value = resource.data as CommerceModel
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

    fun setCommercesMutableLiveData(getCommercesMutableLiveData: MutableLiveData<CommerceModel>) {
        this.getCommercesMutableLiveData = getCommercesMutableLiveData
    }

    fun getCommercesMutableLiveData() : MutableLiveData<CommerceModel> {
        return getCommercesMutableLiveData
    }
}
package com.jorgegomezdeveloper.waycommerce.ui.features.listcommerces.viewmodel

import androidx.lifecycle.MutableLiveData
import com.jorgegomezdeveloper.waycommerce.data.network.resource.Resource
import com.jorgegomezdeveloper.waycommerce.ui.base.WCBaseFragment
import com.jorgegomezdeveloper.waycommerce.ui.base.WCBaseViewModel
import com.jorgegomezdeveloper.waycommerce.usercases.GetCommerces

class WCListCommercesViewModel: WCBaseViewModel() {

    // USE CASES
    private var getCommercesMutableLiveData: MutableLiveData<Any> = MutableLiveData()

    // USE CASES

    fun getCommerces(getComic: GetCommerces, fragment: WCBaseFragment) {

        getComic.execute()
            .observe(fragment, { resource ->

                if (resource != null) {

                    when (resource.status) {

                        Resource.Status.SUCCESS -> {
                            getCommercesMutableLiveData.value = resource.data as Any
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

    fun setCommercesMutableLiveData(getCommercesMutableLiveData: MutableLiveData<Any>) {
        this.getCommercesMutableLiveData = getCommercesMutableLiveData
    }

    fun getCommercesMutableLiveData() : MutableLiveData<Any> {
        return getCommercesMutableLiveData
    }

}
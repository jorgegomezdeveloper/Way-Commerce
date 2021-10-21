package com.jorgegomezdeveloper.waycommerce.data.network.rest

import androidx.lifecycle.LiveData
import com.jorgegomezdeveloper.waycommerce.data.network.resource.Resource
import com.jorgegomezdeveloper.waycommerce.model.CommerceModel

interface WCDataSource {

// =================================================================================================
// API
// =================================================================================================

    fun getCommerces(): LiveData<Resource<CommerceModel>>
}
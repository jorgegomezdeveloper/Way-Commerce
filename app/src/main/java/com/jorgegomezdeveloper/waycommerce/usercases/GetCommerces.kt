package com.jorgegomezdeveloper.waycommerce.usercases

import androidx.lifecycle.LiveData
import com.jorgegomezdeveloper.waycommerce.data.network.resource.Resource
import com.jorgegomezdeveloper.waycommerce.data.repository.WCRepositoryImpl

class GetCommerces(private val wcRepositoryImpl: WCRepositoryImpl) {

    fun execute(): LiveData<Resource<Any>>
}

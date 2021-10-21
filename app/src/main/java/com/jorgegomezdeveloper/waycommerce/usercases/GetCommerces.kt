package com.jorgegomezdeveloper.waycommerce.usercases

import androidx.lifecycle.LiveData
import com.jorgegomezdeveloper.waycommerce.data.network.resource.Resource
import com.jorgegomezdeveloper.waycommerce.data.repository.WCRepositoryImpl
import com.jorgegomezdeveloper.waycommerce.model.CommerceModel

/**
 *   @author Jorge G.A.
 *   @since 21/10/2021
 *   @email jorgegomezdeveloper@gmail.com
 *
 *   Case use for connect data and view model of list commerces.
 */
class GetCommerces(private val wcRepositoryImpl: WCRepositoryImpl) {

    fun execute(): LiveData<Resource<CommerceModel>> =
        wcRepositoryImpl.getWCDataSource().getCommerces()
}

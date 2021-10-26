package com.jorgegomezdeveloper.waycommerce.usercases

import androidx.lifecycle.LiveData
import com.jorgegomezdeveloper.waycommerce.data.network.resource.Resource
import com.jorgegomezdeveloper.waycommerce.data.repository.WCRepositoryImpl
import com.jorgegomezdeveloper.waycommerce.model.Commerce

/**
 *   @author Jorge G.A.
 *   @since 21/10/2021
 *   @email jorgegomezdeveloper@gmail.com
 *
 *   Use case class for connect remote data and view model of the list commerces.
 */
class GetCommerces(private val wcRepositoryImpl: WCRepositoryImpl) {

    fun execute(): LiveData<Resource<List<Commerce>>> =
        wcRepositoryImpl.getWCDataSource().getCommerces()
}

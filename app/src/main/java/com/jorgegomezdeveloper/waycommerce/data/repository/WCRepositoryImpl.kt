package com.jorgegomezdeveloper.waycommerce.data.repository

import android.app.Application
import com.jorgegomezdeveloper.waycommerce.data.network.rest.WCDataSource
import com.jorgegomezdeveloper.waycommerce.data.network.rest.WCRestDataSource
import org.koin.android.ext.android.inject

/**
 *   @author Jorge G.A.
 *   @since 21/10/2021
 *   @email jorgegomezdeveloper@gmail.com
 *
 *   Class for get different Rest Data Sources.
 */
class WCRepositoryImpl: WCRepository, Application() {

// =================================================================================================
// Attributes
// =================================================================================================

    private val wcRestDataSource: WCRestDataSource by inject()

    override fun getWCDataSource(): WCDataSource {
        return wcRestDataSource
    }
}
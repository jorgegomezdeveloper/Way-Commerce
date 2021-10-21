package com.jorgegomezdeveloper.waycommerce.data.repository

import android.app.Application
import com.jorgegomezdeveloper.waycommerce.data.network.rest.WCDataSource
import com.jorgegomezdeveloper.waycommerce.data.network.rest.WCRestDataSource
import org.koin.android.ext.android.inject

class WCRepositoryImpl: WCRepository, Application() {

// =================================================================================================
// Attributes
// =================================================================================================

    private val wcRestDataSource: WCRestDataSource by inject()

    override fun getWCDataSource(): WCDataSource {
        return wcRestDataSource
    }
}
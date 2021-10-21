package com.jorgegomezdeveloper.waycommerce.data.network.rest

import android.app.Application
import com.jorgegomezdeveloper.waycommerce.data.services.commons.retrofit.RetrofitManager
import org.koin.android.ext.android.inject

class WCRestDataSource: WCDataSource, Application() {

// =================================================================================================
// Singletons
// =================================================================================================

    private val retrofitManager: RetrofitManager by inject()

// =================================================================================================
// Override methods
// =================================================================================================

}
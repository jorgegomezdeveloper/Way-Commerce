package com.jorgegomezdeveloper.waycommerce.ui.activities

import com.jorgegomezdeveloper.waycommerce.R
import com.jorgegomezdeveloper.waycommerce.ui.base.WCBaseActivity
import com.jorgegomezdeveloper.waycommerce.ui.base.WCBaseFragment
import com.jorgegomezdeveloper.waycommerce.ui.features.listcommerces.view.fragment.WCListCommercesFragment
import org.koin.android.ext.android.inject

class WCMainActivity: WCBaseActivity() {

// =================================================================================================
// Injects
// =================================================================================================

    private val wcListCommercesFragment: WCListCommercesFragment by inject()

// =================================================================================================
// Config
// =================================================================================================

    override fun getActivityLayout(): Int {
        return R.layout.activity_main
    }

    override fun getActivityTag(): String? {
        return WCMainActivity::class.java.simpleName
    }

    override fun getInitialFragment(): WCBaseFragment {
        return wcListCommercesFragment
    }

    override fun initialize() {
    }
}
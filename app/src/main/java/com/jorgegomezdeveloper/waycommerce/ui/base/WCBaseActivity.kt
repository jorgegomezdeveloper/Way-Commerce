package com.jorgegomezdeveloper.waycommerce.ui.base

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.jorgegomezdeveloper.waycommerce.R
import com.jorgegomezdeveloper.waycommerce.ui.activities.WCMainActivity
import org.koin.android.ext.android.inject

/**
 *   @author Jorge G.A.
 *   @since 21/10/2021
 *   @email jorgegomezdeveloper@gmail.com
 *
 *   Parent class for the activities.
 */
abstract class WCBaseActivity: AppCompatActivity() {

// =================================================================================================
// Config
// =================================================================================================

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(getActivityLayout())
        loadInitialFragment()
    }

// =================================================================================================
// Public methods
// =================================================================================================

    open fun loadInitialFragment() {

        val initialFragment: WCBaseFragment? = getInitialFragment()

        if (initialFragment != null) {
            supportFragmentManager
                .beginTransaction()
                .replace(
                    R.id.frameContainer,
                    initialFragment)
                .commit()
        }
    }

// =================================================================================================
// To be override...
// =================================================================================================

    protected abstract fun getActivityLayout(): Int

    protected abstract fun getActivityTag(): String?

// =================================================================================================
// Abstract methods
// =================================================================================================

    protected abstract fun getInitialFragment(): WCBaseFragment?

    protected abstract fun initialize()
}
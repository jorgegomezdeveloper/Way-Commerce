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

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
    }

    override fun onResume() {
        super.onResume()
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

    protected open fun initializeViews() {}

    protected open fun initializeListeners() {}

    protected open fun loadData() {}

    protected abstract fun getActivityLayout(): Int

    protected abstract fun getActivityTag(): String?

// =================================================================================================
// Abstract methods
// =================================================================================================

    protected abstract fun getInitialFragment(): WCBaseFragment?

    protected abstract fun initialize()

// =================================================================================================
// Private methods
// =================================================================================================

    private fun finishActivity(finishActivity: Boolean) {
        if (finishActivity) {
            this.finish()
        }
    }
}
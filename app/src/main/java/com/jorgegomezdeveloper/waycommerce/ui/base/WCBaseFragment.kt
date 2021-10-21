package com.jorgegomezdeveloper.waycommerce.ui.base

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment

/**
 *   @author Jorge G.A.
 *   @since 21/10/2021
 *   @email jorgegomezdeveloper@gmail.com
 *
 *   Parent class for the fragments.
 */
abstract class WCBaseFragment: Fragment() {

// =================================================================================================
// Attributes
// =================================================================================================

    private var mContext: Context? = null

// =================================================================================================
// To be override...
// =================================================================================================

    protected open fun initializeViews() {}

    protected open fun initializeListeners() {}

    protected open fun loadData() {}

    protected open fun updateViews() {}

    protected abstract fun getFragmentLayout(): Int

    protected abstract fun initialize()

// =================================================================================================
// Config
// =================================================================================================

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initialize()
    }

    override fun onCreateView(inflater: LayoutInflater,
                              container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {

        val rootView = inflater.inflate(getFragmentLayout(), container, false)

        if (container != null) {
            mContext = container.context
        }

        initializeViews()
        initializeListeners()
        loadData()

        return rootView
    }
}
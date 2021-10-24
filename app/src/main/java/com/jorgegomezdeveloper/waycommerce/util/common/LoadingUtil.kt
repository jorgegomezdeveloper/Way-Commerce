package com.jorgegomezdeveloper.waycommerce.util.common

import android.app.Activity
import android.view.View
import kotlinx.android.synthetic.main.animation_loading.*

/**
 *   @author Jorge G.A.
 *   @since 22/10/2021
 *   @email jorgegomezdeveloper@gmail.com
 *
 *   Util object for control of loading in the calls to services or others.
 */
object LoadingUtil {

    fun showLoading(activity : Activity) {
        activity.loadingAnimation.visibility = View.VISIBLE
        activity.loadingAnimation.playAnimation()
    }

    fun hideLoading(activity : Activity) {
        activity.loadingAnimation.cancelAnimation()
        activity.loadingAnimation.visibility = View.GONE
    }
}
package com.jorgegomezdeveloper.waycommerce.data.services.commons.retrofit

import com.google.gson.Gson
import com.jorgegomezdeveloper.waycommerce.data.network.service.WCApiService
import com.jorgegomezdeveloper.waycommerce.util.retrofit.RetrofitUtil
import retrofit2.Retrofit

/**
 *   @author Jorge G.A.
 *   @since 21/10/2021
 *   @email jorgegomezdeveloper@gmail.com
 *
 *   Manager class for initialize Retrofit.
 */
class RetrofitManager(private val gson: Gson) {

// =================================================================================================
// Constants
// =================================================================================================

    companion object {
        private const val HOST_PRO = "http://prod.klikin.com"
    }

// =================================================================================================
// Attributes
// =================================================================================================

    private var retrofit: Retrofit? = null
    private var wcApiService: WCApiService? = null
    private var hostPro: String? = null

// =================================================================================================
// Initialize
// =================================================================================================

    /**
     * Called to initialize the retrofit and the api.
     */
    init {
        hostPro = HOST_PRO
        setDefaultRetrofit()
    }

// =================================================================================================
// Public methods
// =================================================================================================

    /**
     * Call this methods to free the resources.
     */
    fun freeResources() {
        this.retrofit = null
        this.wcApiService = null
    }

// =================================================================================================
// Getters && Setters
// =================================================================================================

    /**
     * Get the api service.
     * Important: Get this API to call the end points.
     */
    fun getWCApiService(): WCApiService? = this.wcApiService

// =================================================================================================
// Getters && Setters
// =================================================================================================

    /**
     * Called to set de default retrofit and creates the api service.
     */
    private fun setDefaultRetrofit() {
        this.retrofit = RetrofitUtil.getRetrofitWithHeaders(gson)
        this.wcApiService = this.retrofit!!.create(WCApiService::class.java)
    }
}
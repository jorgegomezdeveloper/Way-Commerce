package com.jorgegomezdeveloper.waycommerce.data.network.service

import com.jorgegomezdeveloper.waycommerce.model.Commerce
import retrofit2.Call
import retrofit2.http.GET

/**
 *   @author Jorge G.A.
 *   @since 21/10/2021
 *   @email jorgegomezdeveloper@gmail.com
 *
 *   Routes and methods for call to end points.
 */
interface WCApiService {

    @GET("/commerces/public")
    fun getCommerces(): Call<List<Commerce>>
}
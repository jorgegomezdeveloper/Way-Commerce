package com.jorgegomezdeveloper.waycommerce.model

import com.google.gson.annotations.SerializedName

/**
 *   @author Jorge G.A.
 *   @since 21/10/2021
 *   @email jorgegomezdeveloper@gmail.com
 *
 *   Model of data for commerce.
 */
data class CommerceModel(

    @SerializedName("name")
    val name: String?,
    @SerializedName("category")
    val category: String?,
    @SerializedName("shortDescription")
    val shortDescription: String?
)
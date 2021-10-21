package com.jorgegomezdeveloper.waycommerce.data.repository

import com.jorgegomezdeveloper.waycommerce.data.network.rest.WCDataSource

interface WCRepository {
    fun getWCDataSource(): WCDataSource
}
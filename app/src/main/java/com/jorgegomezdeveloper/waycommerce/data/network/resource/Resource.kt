package com.jorgegomezdeveloper.waycommerce.data.network.resource

import com.jorgegomezdeveloper.waycommerce.data.network.AppException

/**
 *   @author Jorge G.A.
 *   @since 21/10/2021
 *   @email jorgegomezdeveloper@gmail.com
 *
 *   Class of logic for the status of the rest responses.
 */
class Resource<T> private constructor(val status: Status,
                                      val data: T?,
                                      val exception: AppException?) {
    var code: Int = -1

    enum class Status {
        SUCCESS, ERROR, DATA_NOT_AVAILABLE
    }

    companion object {

        fun <T> success(data: T?): Resource<T> {
            return if (data != null)
                Resource(Status.SUCCESS, data, null)
            else
                Resource(Status.DATA_NOT_AVAILABLE, data, null)
        }

        fun <T> error(exception: AppException?): Resource<T> {
            return Resource(Status.ERROR, null, exception)
        }

    }
}
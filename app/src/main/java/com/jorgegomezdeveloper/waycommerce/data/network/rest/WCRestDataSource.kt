package com.jorgegomezdeveloper.waycommerce.data.network.rest

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.jorgegomezdeveloper.waycommerce.data.network.AppException
import com.jorgegomezdeveloper.waycommerce.data.network.resource.Resource
import com.jorgegomezdeveloper.waycommerce.data.services.commons.retrofit.RetrofitManager
import com.jorgegomezdeveloper.waycommerce.model.Commerce
import org.koin.android.ext.android.inject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

/**
 *   @author Jorge G.A.
 *   @since 21/10/2021
 *   @email jorgegomezdeveloper@gmail.com
 *
 *   Class for the logic of a data source,
 *   in calls and responses from remote services.
 */
class WCRestDataSource: WCDataSource, Application() {

// =================================================================================================
// Singletons
// =================================================================================================

    private val retrofitManager: RetrofitManager by inject()

// =================================================================================================
// Override methods
// =================================================================================================

    override fun getCommerces(): LiveData<Resource<List<Commerce>>> {

        val data = MutableLiveData<Resource<List<Commerce>>>()

        retrofitManager
            .getWCApiService()!!
            .getCommerces().enqueue(object : Callback<List<Commerce>> {
                override fun onResponse(call: Call<List<Commerce>>?,
                                        response: Response<List<Commerce>>?) {

                    if (response != null && response.isSuccessful) {
                        data.value = Resource.success(response.body())
                    } else {

                        val code = response?.code()
                        val resource = Resource.success(response?.body())
                        resource.code = code!!
                        data.value = resource
                    }
                }

                override fun onFailure(call: Call<List<Commerce>>?, t: Throwable?) {

                    val exception = AppException(t)
                    data.value = Resource.error(exception)
                }
            })

        return data
    }

}
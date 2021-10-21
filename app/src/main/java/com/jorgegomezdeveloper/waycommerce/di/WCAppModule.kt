package com.jorgegomezdeveloper.waycommerce.di

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.jorgegomezdeveloper.waycommerce.data.network.rest.WCRestDataSource
import com.jorgegomezdeveloper.waycommerce.data.network.service.WCApiService
import com.jorgegomezdeveloper.waycommerce.data.services.commons.retrofit.RetrofitManager
import com.jorgegomezdeveloper.waycommerce.ui.features.listcommerces.view.fragment.WCListCommercesFragment
import com.jorgegomezdeveloper.waycommerce.ui.features.listcommerces.viewmodel.WCListCommercesViewModel
import com.jorgegomezdeveloper.waycommerce.util.retrofit.RetrofitUtil
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import retrofit2.Retrofit

/**
 *   @author Jorge G.A.
 *   @since 21/10/2021
 *   @email jorgegomezdeveloper@gmail.com
 *
 *   Class for to declare the modules for the dependencies injections.
 */

// =================================================================================================
// UTILS modules
// =================================================================================================

val wcUtilsModule = module {

}

// =================================================================================================
// NETWORK modules
// =================================================================================================

val wcNetworksModule = module {

    fun provideGson(): Gson {
        return GsonBuilder().create()
    }

    fun provideRetrofit(gson: Gson): Retrofit {
        return RetrofitUtil.getRetrofitWithHeaders(gson)
    }

    fun provideNetworkService(retrofit: Retrofit): WCApiService {
        return retrofit.create(WCApiService::class.java)
    }

    single { provideGson() }
    single { provideRetrofit(get()) }
    single { provideNetworkService(get()) }
    single { RetrofitManager(get()) }
}

// =================================================================================================
// DATA modules
// =================================================================================================

val wcRestDataSourcesModule = module {
    single { WCRestDataSource() }
}

// =================================================================================================
// USE CASES module
// =================================================================================================

val wcUseCasesImplModule = module {

}

// =================================================================================================
// VIEW MODEL modules
// =================================================================================================

val wcViewModelsModule = module {
    viewModel { WCListCommercesViewModel() }
}

// =================================================================================================
// FRAGMENT modules
// =================================================================================================

val wcFragmentsModule = module {
    factory { WCListCommercesFragment() }
}

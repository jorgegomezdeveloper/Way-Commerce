package com.jorgegomezdeveloper.waycommerce.app

import android.app.Application
import androidx.appcompat.app.AppCompatDelegate
import com.jorgegomezdeveloper.waycommerce.di.*
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidFileProperties
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.core.logger.Level

/**
 *   @author Jorge G.A.
 *   @since 21/10/2021
 *   @email jorgegomezdeveloper@gmail.com
 *
 *   Application class for some initializations.
 */
class WayCommerce: Application() {

    override fun onCreate() {
        super.onCreate()
        //Initialize KOIN
        initializeKoin()
        AppCompatDelegate.setCompatVectorFromResourcesEnabled(true)
    }

    /**
     * INITIALIZE KOIN
     */
    private fun initializeKoin() {

        startKoin {
            androidLogger(Level.DEBUG)
            androidContext(this@WayCommerce)
            androidFileProperties()

            //Declare modules.
            modules(listOf(
                //MANAGERS
                //UTILS
                wcUtilsModule,
                //DATA
                wcNetworksModule, wcRestDataSourcesModule,
                //USE CASES
                wcUseCasesImplModule,
                //VIEW MODELS
                wcViewModelsModule,
                //FRAGMENTS
                wcFragmentsModule))
        }
    }

    companion object {
        lateinit var instance: WayCommerce
            private set
    }
}
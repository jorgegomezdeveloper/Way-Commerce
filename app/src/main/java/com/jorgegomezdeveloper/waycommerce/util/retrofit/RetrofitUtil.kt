package com.jorgegomezdeveloper.waycommerce.util.retrofit

import androidx.databinding.ktx.BuildConfig
import com.google.gson.Gson
import com.jorgegomezdeveloper.waycommerce.common.constants.Constants
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.ResponseBody
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Converter
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.lang.reflect.Type

/**
 *   @author Jorge G.A.
 *   @since 21/10/2021
 *   @email jorgegomezdeveloper@gmail.com
 *
 *   Util object for to configure Retrofit and initialize client for the remote services.
 */
object RetrofitUtil {

// =================================================================================================
// Retrofit methods
// =================================================================================================

    fun getRetrofitWithHeaders(gson: Gson): Retrofit {
        class HeaderInterceptor : Interceptor {
            override fun intercept(chain: Interceptor.Chain) = chain.run {
                proceed(
                    request()
                        .newBuilder()
                        .build()
                )
            }
        }

        val interceptorApp = HeaderInterceptor()

        val interceptor = HttpLoggingInterceptor()
        interceptor
            .setLevel(
                if (BuildConfig.DEBUG)
                    HttpLoggingInterceptor.Level.BODY
                else HttpLoggingInterceptor.Level.NONE)
        val client = OkHttpClient.Builder()
            .addInterceptor(interceptor)
            .addNetworkInterceptor(interceptorApp).build()


        val nullOnEmptyConverterFactory = object : Converter.Factory() {
            fun converterFactory() = this
            override fun responseBodyConverter(
                type: Type, annotations: Array<out Annotation>,
                retrofit: Retrofit
            ) = object : Converter<ResponseBody, Any?> {

                val nextResponseBodyConverter =
                    retrofit
                        .nextResponseBodyConverter<Any?>(
                            converterFactory(),
                            type, annotations)

                override fun convert(value: ResponseBody) =
                    if (value.contentLength() != 0L)
                        nextResponseBodyConverter.convert(value) else null
            }
        }
        return Retrofit.Builder()
            .addConverterFactory(nullOnEmptyConverterFactory)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .client(client)
            .baseUrl(Constants.HOST_PROD).build()
    }
}
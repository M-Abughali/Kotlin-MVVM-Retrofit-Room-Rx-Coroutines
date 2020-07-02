package com.ghalym.task.network

import android.app.Application
import android.content.Context
import android.net.ConnectivityManager
import com.ghalym.task.BuildConfig

import com.ghalym.task.util.Constants.INSTANCE.NO_INTERNET_CONNECTION_MSG
import okhttp3.Interceptor
import okhttp3.Response
import javax.inject.Inject

class NetworkInterceptor @Inject constructor(val application: Application) : Interceptor {

    private val API_KEY = "x-api-key"
    private val PAGINATION_LIMIT = "limit"
    private val PAGINATION_LIMIT_VALUE = "20"


    private fun isInternetAvailable(): Boolean {
        val info = application.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        info.activeNetworkInfo.also {

            return it != null && it.isConnected
        }
    }


    override fun intercept(chain: Interceptor.Chain): Response {

        var request = chain.request()
        val url = request
            .url
            .newBuilder()
            .addQueryParameter(PAGINATION_LIMIT, PAGINATION_LIMIT_VALUE)
            .build()

        request = request.newBuilder()
            .addHeader(API_KEY, BuildConfig.API_TOKEN)
            .method(request.method, request.body).url(url).build()


        if (!isInternetAvailable())
            throw  NoInternetException(NO_INTERNET_CONNECTION_MSG)

        return chain.proceed(request)

    }

}
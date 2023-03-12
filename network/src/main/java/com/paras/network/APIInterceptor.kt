package com.paras.network

import okhttp3.Interceptor
import okhttp3.Response
import java.io.IOException
import javax.inject.Inject
import javax.inject.Singleton


@Singleton
class ApiInterceptor @Inject constructor() : Interceptor {

    @Throws(IOException::class)
    override fun intercept(chain: Interceptor.Chain): Response {
        val original = chain.request()
        val originalHttpUrl = original.url

        val url = originalHttpUrl.newBuilder()
            .build()

        val requestBuilder = original.newBuilder()
            .addHeader(APIConstants.HEADER_KEY_X_RAPID_API, APIConstants.HEADER_VALUE_X_RAPID_API)
            .addHeader(APIConstants.HEADER_KEY_X_RAPID_HOST, APIConstants.HEADER_VALUE_X_RAPID_HOST)
            .url(url)

        val request = requestBuilder.build()
        return chain.proceed(request)
    }
}
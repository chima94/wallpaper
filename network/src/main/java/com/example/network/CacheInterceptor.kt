package com.example.network

import android.content.Context
import com.crazylegend.common.isOnline
import okhttp3.Interceptor
import okhttp3.Response
import okio.IOException

class CacheInterceptor(private val context: Context) : Interceptor {

    @Throws(IOException::class)
    override fun intercept(chain: Interceptor.Chain): Response {
        var request = chain.request()
        if(!context.isOnline){
            request = request.newBuilder()
                .removeHeader("Pragma")
                .header("Cache-Control", "public, only-if-cached, max-stale=" + 2419200)
                .build()
        }
        return chain.proceed(request)
    }
}
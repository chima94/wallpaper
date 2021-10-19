package com.example.network

import android.content.Context
import com.example.constants.Constants
import com.example.network.CacheInterceptor
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.Cache
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.io.File
import java.util.concurrent.TimeUnit
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Singleton
    @Provides
    fun provideCacheInterceptor(@ApplicationContext context: Context) : CacheInterceptor{
        return CacheInterceptor(context)
    }

    @Singleton
    @Provides
    fun provideOkHttpClient(
        @ApplicationContext context: Context,
        cacheInterceptor: CacheInterceptor
    ): OkHttpClient{
        return OkHttpClient()
            .newBuilder()
            .addInterceptor(cacheInterceptor)
            .cache(Cache(File(context.cacheDir, "ResponsesCache"), (30 * 1024 * 1024).toLong()))
            .connectTimeout(20, TimeUnit.SECONDS)
            .readTimeout(20, TimeUnit.SECONDS)
            .writeTimeout(20, TimeUnit.SECONDS)
            .build()
    }


    @Singleton
    @Provides
    fun provideGsonBuilder(): Gson {
        return GsonBuilder().create()
    }

    @Singleton
    @Provides
    fun providesretrofit(
        gsonBuilder: Gson,
        okHttpClient: OkHttpClient
    ): Retrofit.Builder{
        return Retrofit.Builder()
            .baseUrl(Constants.BASE_URL)
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create(gsonBuilder))

    }

    @Singleton
    @Provides
    fun provideApiService(retrofit: Retrofit.Builder): ApiService{
        return retrofit
            .build()
            .create(ApiService::class.java)
    }
}
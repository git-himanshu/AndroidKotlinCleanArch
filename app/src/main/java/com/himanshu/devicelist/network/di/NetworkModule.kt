package com.himanshu.devicelist.network.di

import com.himanshu.devicelist.network.DevicesApi
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
class NetworkModule {
    private val BASE_URL = "https://api-mobilespecs.azharimm.site"

    @Singleton
    @Provides
    fun provideDevicesApi(): DevicesApi {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .build()
            .create(DevicesApi::class.java)
    }
}
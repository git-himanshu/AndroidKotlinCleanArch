package com.himanshu.bike_network_list.network.di

import com.himanshu.bike_network_list.network.BASE_URL
import com.himanshu.bike_network_list.data.datasource.remote_datasource.BikeNetworkApi
import com.himanshu.bike_network_list.network.ErrorInterceptor
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
class NetworkModule {
    @Singleton
    @Provides
    fun provideDevicesApi(): BikeNetworkApi {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .client(httpClient())
            .build()
            .create(BikeNetworkApi::class.java)
    }

    private fun httpClient(): OkHttpClient {
        val builder = OkHttpClient().newBuilder()
            .addInterceptor(ErrorInterceptor())
        return builder.build()
    }
}
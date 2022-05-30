package com.himanshu.devicelist.network

import io.reactivex.Single
import retrofit2.http.GET

interface DevicesApi {

    @GET("v2/brands/apple-phones-48")
    fun getDeviceListData(): Single<DeviceListContainerDto>

}
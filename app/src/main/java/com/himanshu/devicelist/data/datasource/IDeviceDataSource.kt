package com.himanshu.devicelist.data.datasource

import com.himanshu.devicelist.domain.entity.Device
import io.reactivex.Single

interface IDeviceDataSource {
    fun getDeviceList(): Single<List<Device>>
}
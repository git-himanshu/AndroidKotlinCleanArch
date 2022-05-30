package com.himanshu.devicelist.network

import com.himanshu.devicelist.data.datasource.IDeviceDataSource
import com.himanshu.devicelist.domain.entity.Device
import io.reactivex.Single
import javax.inject.Inject

class DeviceRemoteDataSource @Inject constructor(val api: DevicesApi) : IDeviceDataSource {
    override fun getDeviceList(): Single<List<Device>> =
        api.getDeviceListData().map { it.asDomainModel() }
}
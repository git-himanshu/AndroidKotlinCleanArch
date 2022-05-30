package com.himanshu.devicelist.data.repository

import com.himanshu.devicelist.domain.entity.Device
import com.himanshu.devicelist.domain.repository.IDeviceRepository
import com.himanshu.devicelist.network.DeviceRemoteDataSource
import io.reactivex.Single
import javax.inject.Inject

class DeviceRepository @Inject constructor(private val dataSource: DeviceRemoteDataSource) : IDeviceRepository {
    override fun getDeviceList(): Single<List<Device>> = dataSource.getDeviceList()
}
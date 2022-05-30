package com.himanshu.devicelist.domain.repository

import com.himanshu.devicelist.domain.entity.Device
import io.reactivex.Single

interface IDeviceRepository {
    fun getDeviceList():Single<List<Device>>
}
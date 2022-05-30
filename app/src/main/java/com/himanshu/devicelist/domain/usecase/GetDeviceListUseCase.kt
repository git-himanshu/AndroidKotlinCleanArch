package com.himanshu.devicelist.domain.usecase

import com.himanshu.devicelist.data.repository.DeviceRepository
import com.himanshu.devicelist.domain.entity.Device
import io.reactivex.Single
import javax.inject.Inject

class GetDeviceListUseCase @Inject constructor(private val repository: DeviceRepository) {
    operator fun invoke(): Single<List<Device>> = repository.getDeviceList()
}
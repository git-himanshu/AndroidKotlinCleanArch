package com.himanshu.devicelist.domain.usecase

import com.himanshu.devicelist.data.repository.DeviceRepository
import com.himanshu.devicelist.domain.entity.Device
import io.reactivex.Single
import javax.inject.Inject

class GetSortedDeviceListUseCase @Inject constructor(private val repository: DeviceRepository) {
    operator fun invoke(order: Order): Single<List<Device>> {
        return when (order) {
            Order.ASCENDING -> repository.getDeviceList()
                .map {
                    it.sortedBy { it.name?.lowercase() }
                }
            Order.DESCENDING -> repository.getDeviceList()
                .map {
                    it.sortedByDescending { it.name?.lowercase() }
                }
        }
    }
}

enum class Order {
    ASCENDING, DESCENDING
}
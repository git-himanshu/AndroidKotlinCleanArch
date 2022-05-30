package com.himanshu.devicelist.network

import com.google.gson.annotations.SerializedName
import com.himanshu.devicelist.domain.entity.Device

data class DeviceListContainerDto(
    @SerializedName("data")
    val data: DeviceListDto?
)

data class DeviceListDto(
    @SerializedName("phones")
    val devices: List<DeviceDto>?
)

data class DeviceDto(
    @SerializedName("phone_name")
    val name: String?,
    @SerializedName("slug")
    val slug: String?,
    @SerializedName("image")
    val image: String?,
)

/**
 * Convert Network results to database objects
 */
fun DeviceListContainerDto.asDomainModel(): List<Device>? {
    return data?.devices?.map {
        Device(
            name = it.name,
            slug = it.slug,
            image = it.image
        )
    }
}

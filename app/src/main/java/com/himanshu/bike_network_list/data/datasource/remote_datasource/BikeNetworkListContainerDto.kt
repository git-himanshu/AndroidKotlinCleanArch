package com.himanshu.bike_network_list.network

import com.google.gson.annotations.SerializedName

data class BikeNetworkListContainerDto(
    @SerializedName("networks")
    val bikeNetworks: List<BikeNetworkDto>?
)

data class BikeNetworkDto(
    @SerializedName("id")
    val id: String?,
    @SerializedName("name")
    val name: String?,
    @SerializedName("href")
    val href: String?,
    @SerializedName("location")
    val location: Location?,
)

data class Location(
    @SerializedName("city")
    val city: String?,
    @SerializedName("country")
    val country: String?,
    @SerializedName("latitude")
    val latitude: Double?,
    @SerializedName("longitude")
    val longitude: Double?,
)
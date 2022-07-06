package com.himanshu.bike_network_list.data.datasource

import com.himanshu.bike_network_list.network.BikeNetworkDto
import io.reactivex.Single

interface IBikeNetworkDataSource {
    fun getBikeNetworkList(): Single<List<BikeNetworkDto>>
}
package com.himanshu.bike_network_list.domain.repository

import com.himanshu.bike_network_list.domain.entity.BikeNetworkEntity
import io.reactivex.Single

interface IBikeNetworkRepository {
    fun getBikeNetworkList():Single<List<BikeNetworkEntity>>
}
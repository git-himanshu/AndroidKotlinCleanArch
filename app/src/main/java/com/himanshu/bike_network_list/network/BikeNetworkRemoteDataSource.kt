package com.himanshu.bike_network_list.network

import com.himanshu.bike_network_list.data.datasource.IBikeNetworkDataSource
import io.reactivex.Single
import javax.inject.Inject

class BikeNetworkRemoteDataSource @Inject constructor(val api: BikeNetworkApi) :
    IBikeNetworkDataSource {
    override fun getBikeNetworkList(): Single<List<BikeNetworkDto>> =
        api.getBikeNetworkList().map {
            it.bikeNetworks
        }
}
package com.himanshu.bike_network_list.data.datasource.remote_datasource

import com.himanshu.bike_network_list.network.BikeNetworkListContainerDto
import com.himanshu.bike_network_list.network.EndPoints
import io.reactivex.Single
import retrofit2.http.GET

interface BikeNetworkApi {

    @GET(EndPoints.BIKE_NETWORK_ENDPOINT)
    fun getBikeNetworkList(): Single<BikeNetworkListContainerDto>

}
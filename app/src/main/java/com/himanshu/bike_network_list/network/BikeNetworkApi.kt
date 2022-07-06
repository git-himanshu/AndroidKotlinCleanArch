package com.himanshu.bike_network_list.network

import io.reactivex.Single
import retrofit2.http.GET

interface BikeNetworkApi {

    @GET(EndPoints.BIKE_NETWORK_ENDPOINT)
    fun getBikeNetworkList(): Single<BikeNetworkListContainerDto>

}
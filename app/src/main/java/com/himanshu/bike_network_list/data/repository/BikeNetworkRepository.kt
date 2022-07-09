package com.himanshu.bike_network_list.data.repository

import com.himanshu.bike_network_list.data.model_mapper.BikeNetworkRemoteDataMapper
import com.himanshu.bike_network_list.domain.entity.BikeNetworkEntity
import com.himanshu.bike_network_list.domain.repository.IBikeNetworkRepository
import com.himanshu.bike_network_list.data.datasource.remote_datasource.BikeNetworkRemoteDataSource
import io.reactivex.Single
import javax.inject.Inject

class BikeNetworkRepository @Inject constructor(
    private val dataSource: BikeNetworkRemoteDataSource,
    private val modelMapper: BikeNetworkRemoteDataMapper
) :
    IBikeNetworkRepository {
    override fun getBikeNetworkList(): Single<List<BikeNetworkEntity>> =
        dataSource.getBikeNetworkList().map { bikeNetworkList ->
            bikeNetworkList.map { bikeNetwork ->
                modelMapper.fromDtoToEntity(bikeNetwork)
            }
        }
}
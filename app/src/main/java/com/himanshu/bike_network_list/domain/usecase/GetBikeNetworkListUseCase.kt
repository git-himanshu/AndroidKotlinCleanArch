package com.himanshu.bike_network_list.domain.usecase

import com.himanshu.bike_network_list.data.repository.BikeNetworkRepository
import com.himanshu.bike_network_list.domain.entity.BikeNetworkEntity
import io.reactivex.Single
import javax.inject.Inject

class GetBikeNetworkListUseCase @Inject constructor(private val repository: BikeNetworkRepository) {
    operator fun invoke(): Single<List<BikeNetworkEntity>> = repository.getBikeNetworkList()
}
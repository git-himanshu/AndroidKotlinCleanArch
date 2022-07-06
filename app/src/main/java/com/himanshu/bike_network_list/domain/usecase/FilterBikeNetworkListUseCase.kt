package com.himanshu.bike_network_list.domain.usecase

import com.himanshu.bike_network_list.data.repository.BikeNetworkRepository
import com.himanshu.bike_network_list.domain.entity.BikeNetworkEntity
import io.reactivex.Single
import java.util.*
import javax.inject.Inject

class FilterBikeNetworkListUseCase @Inject constructor(private val repository: BikeNetworkRepository) {
    operator fun invoke(filterText: String): Single<List<BikeNetworkEntity>> {
        return repository.getBikeNetworkList().map { list ->
            list.filter { bikeNetwork ->
                val networkName = bikeNetwork.name ?: ""
                networkName.lowercase(Locale.getDefault()).startsWith(
                    filterText.lowercase(
                        Locale.getDefault()
                    )
                )
            }
        }
    }
}

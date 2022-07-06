package com.himanshu.bike_network_list.data.model_mapper

import com.himanshu.bike_network_list.domain.entity.BikeNetworkEntity
import com.himanshu.bike_network_list.network.BikeNetworkDto
import com.himanshu.bike_network_list.network.Location
import javax.inject.Inject

class BikeNetworkRemoteDataMapper @Inject constructor() : IModelMapper<BikeNetworkEntity, BikeNetworkDto> {
    override fun fromEntityToDto(entity: BikeNetworkEntity): BikeNetworkDto {
        return BikeNetworkDto(
            id = entity.id,
            name = entity.name,
            href = entity.href,
            Location(city = entity.city, country = entity.country, 0.0, 0.0)
        )
    }

    override fun fromDtoToEntity(dto: BikeNetworkDto): BikeNetworkEntity {
        return BikeNetworkEntity(
            id = dto.id,
            name = dto.name,
            href = dto.href,
            city = dto.location?.city,
            country = dto.location?.country
        )
    }

}
package com.himanshu.bike_network_list.presentation.model_mapper

import com.himanshu.bike_network_list.domain.entity.BikeNetworkEntity
import com.himanshu.bike_network_list.presentation.model.BikeNetworkUI
import javax.inject.Inject

class BikeNetworkUIModelMapper @Inject constructor(): IModelMapper<BikeNetworkUI, BikeNetworkEntity> {
    override fun fromEntityToUIModel(entity: BikeNetworkEntity): BikeNetworkUI {
        return BikeNetworkUI(
            id = entity.id,
            name = entity.name,
            href = entity.href,
            city = entity.city,
            country = entity.country
        )
    }

    override fun fromUIModelToEntity(uiModel: BikeNetworkUI): BikeNetworkEntity {
        return BikeNetworkEntity(
            id = uiModel.id,
            name = uiModel.name,
            href = uiModel.href,
            city = uiModel.city,
            country = uiModel.country
        )
    }
}
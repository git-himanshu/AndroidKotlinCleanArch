package com.himanshu.bike_network_list.presentation.model_mapper

interface IModelMapper<UIModel,Entity> {
    fun fromEntityToUIModel(entity: Entity): UIModel
    fun fromUIModelToEntity(uiModel:UIModel): Entity
}
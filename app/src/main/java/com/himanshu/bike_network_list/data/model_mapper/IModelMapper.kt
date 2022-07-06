package com.himanshu.bike_network_list.data.model_mapper

interface IModelMapper<Entity, Dto> {
    fun fromEntityToDto(entity: Entity): Dto
    fun fromDtoToEntity(dto: Dto): Entity
}
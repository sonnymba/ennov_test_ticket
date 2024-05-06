package com.ennov.ticketApi.mapper;

import org.mapstruct.BeanMapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.Named;
import org.mapstruct.NullValuePropertyMappingStrategy;

import java.util.List;

public interface GenericMapper<Entity, RequestDTO, ResponseDTO, DetailsDTO> {
    Entity asEntity(RequestDTO dto);

    List<Entity> asEntityList(List<RequestDTO> dtoList);


    //Get one entity
    ResponseDTO asDTO(Entity entity);
    DetailsDTO asDetailsDTO(Entity entity);


    @Named("partialUpdate")
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void partialUpdate(@MappingTarget Entity entity, RequestDTO dto);
}

package com.fullstackschool.backend.mapper;

import com.fullstackschool.backend.DTO.EventDTO;
import com.fullstackschool.backend.entity.Event;
import org.mapstruct.*;

@Mapper(componentModel = "spring")
public interface EventMapper {

    @Mapping(target = "classId", source = "schoolClass.id")
    EventDTO toDTO(Event event);

    @InheritInverseConfiguration
    @Mapping(target = "schoolClass", ignore = true)
    Event toEntity(EventDTO dto);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateEventFromDto(EventDTO dto, @MappingTarget Event entity);
}

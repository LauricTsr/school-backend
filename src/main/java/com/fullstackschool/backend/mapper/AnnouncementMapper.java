package com.fullstackschool.backend.mapper;

import com.fullstackschool.backend.DTO.AnnouncementDTO;
import com.fullstackschool.backend.entity.Announcement;
import org.mapstruct.*;

@Mapper(componentModel = "spring")
public interface AnnouncementMapper {

    AnnouncementDTO toDTO(Announcement announcement);

    @InheritInverseConfiguration
    @Mapping(target = "schoolClass", ignore = true)
    Announcement toEntity(AnnouncementDTO dto);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateAnnouncementFromDto(AnnouncementDTO dto, @MappingTarget Announcement entity);
}

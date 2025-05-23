package com.fullstackschool.backend.mapper;

import com.fullstackschool.backend.DTO.AttendanceDTO;
import com.fullstackschool.backend.entity.Attendance;
import org.mapstruct.*;

@Mapper(componentModel = "spring")
public interface AttendanceMapper {

    AttendanceDTO toDTO(Attendance attendance);

    @InheritInverseConfiguration
    @Mapping(target = "student", ignore = true)
    @Mapping(target = "lesson", ignore = true)
    Attendance toEntity(AttendanceDTO dto);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateAttendanceFromDto(AttendanceDTO dto, @MappingTarget Attendance entity);
}

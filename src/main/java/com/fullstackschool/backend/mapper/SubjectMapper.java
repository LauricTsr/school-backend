package com.fullstackschool.backend.mapper;

import com.fullstackschool.backend.DTO.SubjectDTO;
import com.fullstackschool.backend.entity.Subject;
import com.fullstackschool.backend.entity.Teacher;
import org.mapstruct.*;

import java.util.List;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring")
public interface SubjectMapper {

    @Mapping(target = "teacherIds", source = "teachers")
    SubjectDTO toDTO(Subject subject);

    @InheritInverseConfiguration
    @Mapping(target = "teachers", ignore = true)
    @Mapping(target = "lessons", ignore = true)
    Subject toEntity(SubjectDTO dto);

    default List<String> mapTeachersToIds(List<Teacher> teachers) {
        return teachers == null ? null : teachers.stream()
                .map(Teacher::getId)
                .collect(Collectors.toList());
    }

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateSubjectFromDto(SubjectDTO dto, @MappingTarget Subject entity);
}

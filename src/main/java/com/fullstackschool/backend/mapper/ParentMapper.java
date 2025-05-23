package com.fullstackschool.backend.mapper;

import com.fullstackschool.backend.DTO.ParentDTO;
import com.fullstackschool.backend.entity.Parent;
import com.fullstackschool.backend.entity.Student;
import org.mapstruct.*;

import java.util.List;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring")
public interface ParentMapper {

    @Mapping(target = "studentIds", source = "students")
    ParentDTO toDTO(Parent parent);

    @InheritInverseConfiguration
    @Mapping(target = "students", ignore = true)
    Parent toEntity(ParentDTO dto);

    default List<String> mapStudentsToIds(List<Student> students) {
        return students == null ? null : students.stream()
                .map(Student::getId)
                .collect(Collectors.toList());
    }

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateParentFromDto(ParentDTO dto, @MappingTarget Parent entity);
}

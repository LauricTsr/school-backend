package com.fullstackschool.backend.mapper;

import com.fullstackschool.backend.DTO.GradeDTO;
import com.fullstackschool.backend.entity.Grade;
import com.fullstackschool.backend.entity.SchoolClass;
import com.fullstackschool.backend.entity.Student;
import org.mapstruct.*;

import java.util.List;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring")
public interface GradeMapper {

    @Mapping(target = "classIds", source = "schoolClasses")
    @Mapping(target = "studentIds", source = "students")
    GradeDTO toDTO(Grade grade);

    @InheritInverseConfiguration
    @Mapping(target = "schoolClasses", ignore = true)
    @Mapping(target = "students", ignore = true)
    Grade toEntity(GradeDTO dto);

    default List<Integer> mapClassIds(List<SchoolClass> schoolClasses) {
        return schoolClasses == null ? null : schoolClasses.stream()
                .map(SchoolClass::getId)
                .collect(Collectors.toList());
    }

    default List<String> mapStudentIds(List<Student> students) {
        return students == null ? null : students.stream()
                .map(Student::getId)
                .collect(Collectors.toList());
    }

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateGradeFromDto(GradeDTO dto, @MappingTarget Grade entity);
}

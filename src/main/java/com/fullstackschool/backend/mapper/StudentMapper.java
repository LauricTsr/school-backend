package com.fullstackschool.backend.mapper;

import com.fullstackschool.backend.DTO.StudentDTO;
import com.fullstackschool.backend.entity.Attendance;
import com.fullstackschool.backend.entity.Result;
import com.fullstackschool.backend.entity.Student;
import org.mapstruct.*;

import java.util.List;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring")
public interface StudentMapper {

    @Mapping(target = "attendanceIds", source = "attendances")
    @Mapping(target = "resultIds", source = "results")
    StudentDTO toDTO(Student student);

    @InheritInverseConfiguration
    @Mapping(target = "attendances", ignore = true)
    @Mapping(target = "results", ignore = true)
    @Mapping(target = "parent", ignore = true)
    @Mapping(target = "schoolClass", ignore = true)
    @Mapping(target = "grade", ignore = true)
    Student toEntity(StudentDTO dto);

    default List<Integer> mapAttendancesToIds(List<Attendance> attendances) {
        return attendances == null ? null : attendances.stream()
                .map(Attendance::getId)
                .collect(Collectors.toList());
    }

    default List<Integer> mapResultsToIds(List<Result> results) {
        return results == null ? null : results.stream()
                .map(Result::getId)
                .collect(Collectors.toList());
    }

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateStudentFromDto(StudentDTO dto, @MappingTarget Student entity);
}
